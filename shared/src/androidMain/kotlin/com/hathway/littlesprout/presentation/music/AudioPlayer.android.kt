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
            // Find the file in assets recursively starting from composeResources
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
            // Check if it lists anything, it's likely a directory
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
}

@SuppressLint("StaticFieldLeak")
object AudioPlayerFactory {
    lateinit var context: Context
}

actual fun getAudioPlayer(): AudioPlayer = AndroidAudioPlayer(AudioPlayerFactory.context)
