package com.hathway.littlesprout.presentation.music

class IosAudioPlayer : AudioPlayer {
    override fun play(fileName: String) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun stop() {
    }

    override fun isPlaying(): Boolean = false

    override fun getDuration(): Long = 0L

    override fun getCurrentPosition(): Long = 0L

    override fun seekTo(position: Long) {
    }
}

actual fun getAudioPlayer(): AudioPlayer = IosAudioPlayer()
