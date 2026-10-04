package com.hathway.littlesprout.presentation.music

import platform.AVFAudio.*
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
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

    init {
        setupAudioSession()
    }

    private fun setupAudioSession() {
        try {
            val audioSession = AVAudioSession.sharedInstance()
            audioSession.setCategory(AVAudioSessionCategoryPlayback, error = null)
            audioSession.setActive(true, error = null)
        } catch (e: Exception) {
            println("Error setting up AVAudioSession: $e")
        }
    }

    override fun preload(fileName: String) {
        if (players.containsKey(fileName)) return

        val cleanFileName = fileName.substringAfterLast("/")
        val name = cleanFileName.substringBeforeLast(".")
        val extension = cleanFileName.substringAfterLast(".", "")

        val paths = listOf(
            "compose-resources/composeResources/littlesprout.shared.generated.resources/files",
            "compose-resources/composeResources/com.hathway.littlesprout.shared.generated.resources/files",
            "compose-resources/littlesprout.shared.generated.resources/files",
            "compose-resources/com.hathway.littlesprout.shared.generated.resources/files",
            "compose-resources/files",
            "composeResources/littlesprout.shared.generated.resources/files",
            "composeResources/com.hathway.littlesprout.shared.generated.resources/files",
            "files",
            ""
        )

        var url: NSURL? = null
        for (path in paths) {
            url = if (path.isEmpty()) {
                NSBundle.mainBundle.URLForResource(name, extension)
            } else {
                NSBundle.mainBundle.URLForResource(name, extension, path)
            }
            if (url != null) break
        }

        if (url == null) {
            val bundlePath = NSBundle.mainBundle.resourcePath
            if (bundlePath != null) {
                val fileManager = NSFileManager.defaultManager
                for (p in paths) {
                    val fullPath = if (p.isEmpty()) {
                        "$bundlePath/$cleanFileName"
                    } else {
                        "$bundlePath/$p/$cleanFileName"
                    }
                    if (fileManager.fileExistsAtPath(fullPath)) {
                        url = NSURL.fileURLWithPath(fullPath)
                        break
                    }
                }
            }
        }

        if (url != null) {
            try {
                val player = AVAudioPlayer(contentsOfURL = url, error = null)
                player.prepareToPlay()
                player.delegate = delegate
                players[fileName] = player
            } catch (e: Exception) {
                println("Error creating AVAudioPlayer for $fileName: $e")
            }
        } else {
            println("Audio file not found in bundle: $fileName")
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
            setupAudioSession()
            it.currentTime = 0.0
            it.prepareToPlay()
            if (!it.play()) {
                println("AVAudioPlayer play() returned false for $fileName")
            }
            currentPlayer = it
        } ?: println("Cannot play audio, player is null for $fileName")
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
        setupAudioSession()
        currentPlayer?.play()
    }

    override fun isPlaying(): Boolean = currentPlayer?.playing ?: false

    override fun release() {
        stop()
        players.clear()
    }

    override fun getDuration(): Long = ((currentPlayer?.duration ?: 0.0) * 1000.0).toLong()

    override fun getCurrentPosition(): Long = ((currentPlayer?.currentTime ?: 0.0) * 1000.0).toLong()

    override fun seekTo(position: Long) {
        currentPlayer?.currentTime = position / 1000.0
    }

    override fun onPlaybackComplete(callback: () -> Unit) {
        this.completionCallback = callback
    }
}

actual fun getAudioPlayer(): AudioPlayer = IosAudioPlayer()
