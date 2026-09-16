package com.hathway.littlesprout.presentation.music

import platform.AVFAudio.*
import platform.Foundation.NSBundle
import platform.Foundation.NSURL
import platform.darwin.NSObject
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
class IosAudioPlayer : AudioPlayer {
    private var players = mutableMapOf<String, AVAudioPlayer>()
    private var currentPlayer: AVAudioPlayer? = null
    private var completionCallback: (() -> Unit)? = null

    private val delegate = object : NSObject(), AVAudioPlayerDelegateProtocol {
        override fun audioPlayerDidFinishPlaying(player: AVAudioPlayer, successfully: Boolean) {
            completionCallback?.invoke()
        }
    }

    override fun preload(fileName: String) {
        if (players.containsKey(fileName)) return

        val name = fileName.substringBeforeLast(".")
        val extension = fileName.substringAfterLast(".", "")
        
        val paths = listOf(
            "compose-resources/files",
            "compose-resources/littlesprout.shared.generated.resources/files",
            "compose-resources/com.hathway.littlesprout.shared.generated.resources/files",
            "files",
            null
        )

        var url: NSURL? = null
        for (path in paths) {
            url = NSBundle.mainBundle.URLForResource(name, extension, path)
            if (url != null) break
        }

        if (url != null) {
            try {
                val player = AVAudioPlayer(contentsOfURL = url, error = null)
                player.prepareToPlay()
                player.delegate = delegate
                players[fileName] = player
            } catch (e: Exception) {
                println("iOS AudioPlayer: Failed to load $fileName - ${e.message}")
            }
        }
    }

    override fun preload(fileNames: List<String>) {
        fileNames.forEach { preload(it) }
    }

    override fun play(fileName: String, interruptCurrent: Boolean) {
        if (interruptCurrent) stop()

        val player = players[fileName] ?: run {
            preload(fileName)
            players[fileName]
        }

        player?.let {
            it.currentTime = 0.0
            it.play()
            currentPlayer = it
        }
    }

    override fun stop() {
        currentPlayer?.stop()
        currentPlayer?.currentTime = 0.0
        currentPlayer = null
    }

    override fun pause() {
        currentPlayer?.pause()
    }

    override fun resume() {
        currentPlayer?.play()
    }

    override fun isPlaying(): Boolean = currentPlayer?.playing ?: false

    override fun release() {
        stop()
        players.clear()
    }

    override fun getDuration(): Long = (currentPlayer?.duration ?: 0.0).toLong() * 1000L

    override fun getCurrentPosition(): Long = (currentPlayer?.currentTime ?: 0.0).toLong() * 1000L

    override fun seekTo(position: Long) {
        currentPlayer?.currentTime = position / 1000.0
    }

    override fun onPlaybackComplete(callback: () -> Unit) {
        this.completionCallback = callback
    }
}

actual fun getAudioPlayer(): AudioPlayer = IosAudioPlayer()
