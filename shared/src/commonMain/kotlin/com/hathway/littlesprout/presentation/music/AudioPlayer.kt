package com.hathway.littlesprout.presentation.music

interface AudioPlayer {
    fun play(fileName: String)
    fun pause()
    fun resume()
    fun stop()
    fun isPlaying(): Boolean
}

expect fun getAudioPlayer(): AudioPlayer
