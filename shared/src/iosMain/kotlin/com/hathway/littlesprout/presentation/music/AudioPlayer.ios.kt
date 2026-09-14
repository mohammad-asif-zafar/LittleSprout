package com.hathway.littlesprout.presentation.music

class IosAudioPlayer : AudioPlayer {
    override fun play(fileName: String) {
        // Implementation using AVPlayer
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun stop() {
    }

    override fun isPlaying(): Boolean = false
}

actual fun getAudioPlayer(): AudioPlayer = IosAudioPlayer()
