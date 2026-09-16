package com.hathway.littlesprout.presentation.music

interface AudioPlayer {
    fun preload(fileName: String)
    fun preload(fileNames: List<String>)
    fun play(fileName: String, interruptCurrent: Boolean = true)
    fun stop()
    fun pause()
    fun resume()
    fun isPlaying(): Boolean
    fun release()
    fun getDuration(): Long
    fun getCurrentPosition(): Long
    fun seekTo(position: Long)
    fun onPlaybackComplete(callback: () -> Unit)
}

expect fun getAudioPlayer(): AudioPlayer
