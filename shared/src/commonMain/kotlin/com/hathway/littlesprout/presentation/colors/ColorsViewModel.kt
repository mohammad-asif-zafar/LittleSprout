package com.hathway.littlesprout.presentation.colors

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.ColorItem
import com.hathway.littlesprout.presentation.music.getAudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class ColorsViewModel : ViewModel() {
    private val audioPlayer = getAudioPlayer()

    private val _colors = MutableStateFlow(
        listOf(
            ColorItem("Red", Res.drawable.img_color_red, 0xFFFF0000, "audio_red.mp3"),
            ColorItem("Blue", Res.drawable.img_color_blue, 0xFF0000FF, "audio_blue.mp3"),
            ColorItem("Green", Res.drawable.img_color_green, 0xFF008000, "audio_green.mp3"),
            ColorItem("Yellow", Res.drawable.img_color_yellow, 0xFFFFFF00, "audio_yellow.mp3"),
            ColorItem("Orange", Res.drawable.img_color_orange, 0xFFFFA500, "audio_orange.mp3"),
            ColorItem("Pink", Res.drawable.img_color_pink, 0xFFFFC0CB, "audio_pink.mp3"),
            ColorItem("Brown", Res.drawable.img_color_brown, 0xFFA52A2A, "audio_brown.mp3"),
            ColorItem("Black", Res.drawable.img_color_black, 0xFF000000, "audio_black.mp3")
        )
    )
    val colors = _colors.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    init {
        audioPlayer.preload(_colors.value.mapNotNull { it.soundRes })
        audioPlayer.onPlaybackComplete {
            _isPlaying.value = false
        }
    }

    fun playCurrentAudio() {
        if (_isPlaying.value) {
            stopAudio()
            return
        }
        val current = _colors.value.getOrNull(_currentIndex.value)
        current?.soundRes?.let {
            _isPlaying.value = true
            audioPlayer.play(it)
        }
    }

    fun nextColor() {
        stopAudio()
        if (_currentIndex.value < _colors.value.size - 1) {
            _currentIndex.value++
            playCurrentAudio()
        }
    }

    fun previousColor() {
        stopAudio()
        if (_currentIndex.value > 0) {
            _currentIndex.value--
            playCurrentAudio()
        }
    }

    private fun stopAudio() {
        audioPlayer.stop()
        _isPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
        stopAudio()
    }
}
