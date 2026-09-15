package com.hathway.littlesprout.presentation.music

interface AudioPlayer {
    fun play(fileName: String)
    fun pause()
    fun resume()
    fun stop()
    fun isPlaying(): Boolean
    fun getDuration(): Long
    fun getCurrentPosition(): Long
    fun seekTo(position: Long)
}

expect fun getAudioPlayer(): AudioPlayer
