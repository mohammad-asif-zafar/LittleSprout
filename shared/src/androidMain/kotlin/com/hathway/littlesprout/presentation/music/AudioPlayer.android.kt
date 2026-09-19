package com.hathway.littlesprout.presentation.music

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaMetadataRetriever
import android.media.SoundPool
import kotlinx.coroutines.*

class AndroidAudioPlayer(private val context: Context) : AudioPlayer {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    companion object {
        private const val MAX_STREAMS = 10
        private val soundPool: SoundPool by lazy {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            SoundPool.Builder()
                .setMaxStreams(MAX_STREAMS)
                .setAudioAttributes(attributes)
                .build().apply {
                    setOnLoadCompleteListener { _, _, _ ->
                        // Load complete
                    }
                }
        }

        private val soundMap = mutableMapOf<String, Int>()
        private val durationMap = mutableMapOf<String, Long>()
        private val pathCache = mutableMapOf<String, String>()
    }

    private var mediaPlayer: MediaPlayer? = null
    private var currentStreamId: Int = 0
    private var currentPlayingFile: String? = null
    private var completionCallback: (() -> Unit)? = null
    private var playbackJob: Job? = null

    override fun preload(fileName: String) {
        if (fileName.isBlank() || soundMap.containsKey(fileName)) return

        scope.launch(Dispatchers.IO) {
            val path = resolvePath(fileName)
            if (path == null) {
                return@launch
            }
            try {
                val descriptor = context.assets.openFd(path)
                val soundId = soundPool.load(descriptor, 1)
                soundMap[fileName] = soundId

                // Get duration for completion callback estimation
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                durationMap[fileName] = durationStr?.toLong() ?: 0L
                retriever.release()
                descriptor.close()
            } catch (e: Exception) {
                // Handle failure
            }
        }
    }

    override fun preload(fileNames: List<String>) {
        fileNames.forEach { preload(it) }
    }

    override fun play(fileName: String, interruptCurrent: Boolean) {
        if (fileName.isBlank()) return

        if (interruptCurrent) {
            stop()
        }

        val soundId = soundMap[fileName]
        if (soundId != null) {
            // Play via SoundPool (Short Audio)
            currentStreamId = soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
            if (currentStreamId != 0) {
                currentPlayingFile = fileName
                val duration = durationMap[fileName] ?: 0L
                startCompletionTimer(duration)
            } else {
                playViaMediaPlayer(fileName)
            }
        } else {
            // Fallback to MediaPlayer
            playViaMediaPlayer(fileName)
        }
    }

    private fun playViaMediaPlayer(fileName: String) {
        scope.launch(Dispatchers.IO) {
            val path = resolvePath(fileName)
            if (path == null) {
                return@launch
            }
            withContext(Dispatchers.Main) {
                try {
                    val descriptor = context.assets.openFd(path)
                    
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer()
                    } else {
                        mediaPlayer?.reset()
                    }

                    mediaPlayer?.apply {
                        setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                        descriptor.close()
                        
                        setVolume(1.0f, 1.0f)
                        setOnCompletionListener {
                            currentPlayingFile = null
                            completionCallback?.invoke()
                        }
                        prepare()
                        start()
                    }
                    currentPlayingFile = fileName
                } catch (e: Exception) {
                    // Handle failure
                }
            }
        }
    }

    private fun startCompletionTimer(duration: Long) {
        playbackJob?.cancel()
        if (duration > 0) {
            playbackJob = scope.launch {
                delay(duration)
                currentPlayingFile = null
                completionCallback?.invoke()
            }
        }
    }

    override fun stop() {
        // Stop SoundPool
        if (currentStreamId != 0) {
            soundPool.stop(currentStreamId)
            currentStreamId = 0
        }
        // Stop MediaPlayer
        try {
            mediaPlayer?.let {
                if (it.isPlaying) it.stop()
            }
        } catch (e: Exception) {}

        playbackJob?.cancel()
        currentPlayingFile = null
    }

    override fun pause() {
        if (currentStreamId != 0) soundPool.pause(currentStreamId)
        try {
            if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
        } catch (e: Exception) {}
    }

    override fun resume() {
        if (currentStreamId != 0) soundPool.resume(currentStreamId)
        try {
            mediaPlayer?.start()
        } catch (e: Exception) {}
    }

    override fun isPlaying(): Boolean {
        return currentPlayingFile != null || (mediaPlayer?.isPlaying ?: false)
    }

    override fun getDuration(): Long {
        if (soundMap.containsKey(currentPlayingFile)) {
            return durationMap[currentPlayingFile] ?: 0L
        }
        return try { mediaPlayer?.duration?.toLong() ?: 0L } catch (e: Exception) { 0L }
    }

    override fun getCurrentPosition(): Long {
        return try { mediaPlayer?.currentPosition?.toLong() ?: 0L } catch (e: Exception) { 0L }
    }

    override fun seekTo(position: Long) {
        try {
            mediaPlayer?.seekTo(position.toInt())
        } catch (e: Exception) {}
    }

    override fun onPlaybackComplete(callback: () -> Unit) {
        this.completionCallback = callback
    }

    override fun release() {
        stop()
        mediaPlayer?.release()
        mediaPlayer = null
        // We don't release the static soundPool as it's shared across the app
    }

    private fun resolvePath(fileName: String): String? {
        pathCache[fileName]?.let { return it }

        val targetedPaths = listOf(
            "composeResources/littlesprout.shared.generated.resources/files/$fileName",
            "composeResources/com.hathway.littlesprout.shared.generated.resources/files/$fileName",
            "files/$fileName"
        )

        for (path in targetedPaths) {
            try {
                context.assets.open(path).use { it.close() }
                pathCache[fileName] = path
                return path
            } catch (e: Exception) {
                // Not at this path
            }
        }

        val found = findAssetPath(context, "composeResources", fileName) ?: findAssetPath(context, "", fileName)
        if (found != null) {
            pathCache[fileName] = found
        }
        return found
    }

    private fun findAssetPath(context: Context, root: String, targetFileName: String): String? {
        val assets = context.assets.list(root) ?: return null
        for (asset in assets) {
            val fullPath = if (root.isEmpty()) asset else "$root/$asset"
            if (asset == targetFileName) return fullPath
            if (!asset.contains(".")) {
                val found = findAssetPath(context, fullPath, targetFileName)
                if (found != null) return found
            }
        }
        return null
    }
}

@SuppressLint("StaticFieldLeak")
object AudioPlayerFactory {
    lateinit var context: Context
}

actual fun getAudioPlayer(): AudioPlayer = AndroidAudioPlayer(AudioPlayerFactory.context.applicationContext)
