package com.hathway.littlesprout.presentation.music

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.util.Log

class AndroidAudioPlayer(private val context: Context) : AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    override fun play(fileName: String) {
        Log.d("AudioPlayer", "Requested to play: $fileName")
        stop()
        
        try {
            val path = findAssetPath(context, "composeResources", fileName) 
                ?: findAssetPath(context, "", fileName)
                ?: fileName
            
            Log.d("AudioPlayer", "Resolved asset path: $path")
            
            val descriptor = try {
                context.assets.openFd(path)
            } catch (e: Exception) {
                Log.e("AudioPlayer", "Failed to openFd for $path: ${e.message}")
                null
            }

            if (descriptor != null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                    descriptor.close()
                    setVolume(1.0f, 1.0f)
                    prepare()
                    start()
                }
                Log.i("AudioPlayer", "Playing started: $path")
            } else {
                Log.e("AudioPlayer", "Could not find asset: $fileName in any known location")
            }
            
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Fatal error in play()", e)
        }
    }

    private fun findAssetPath(context: Context, root: String, targetFileName: String): String? {
        val assets = context.assets.list(root) ?: return null
        for (asset in assets) {
            val fullPath = if (root.isEmpty()) asset else "$root/$asset"
            if (asset == targetFileName) {
                return fullPath
            }
            val subAssets = context.assets.list(fullPath)
            if (!subAssets.isNullOrEmpty()) {
                val found = findAssetPath(context, fullPath, targetFileName)
                if (found != null) return found
            }
        }
        return null
    }

    override fun pause() {
        try {
            mediaPlayer?.let { if (it.isPlaying) it.pause() }
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Pause error", e)
        }
    }

    override fun resume() {
        try {
            mediaPlayer?.start()
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Resume error", e)
        }
    }

    override fun stop() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) stop()
                release()
            }
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Stop error", e)
        } finally {
            mediaPlayer = null
        }
    }

    override fun isPlaying(): Boolean = try { mediaPlayer?.isPlaying ?: false } catch (e: Exception) { false }

    override fun getDuration(): Long = try { mediaPlayer?.duration?.toLong() ?: 0L } catch (e: Exception) { 0L }

    override fun getCurrentPosition(): Long = try { mediaPlayer?.currentPosition?.toLong() ?: 0L } catch (e: Exception) { 0L }

    override fun seekTo(position: Long) {
        try {
            mediaPlayer?.seekTo(position.toInt())
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Seek error", e)
        }
    }
}

@SuppressLint("StaticFieldLeak")
object AudioPlayerFactory {
    lateinit var context: Context
}

actual fun getAudioPlayer(): AudioPlayer = AndroidAudioPlayer(AudioPlayerFactory.context)
