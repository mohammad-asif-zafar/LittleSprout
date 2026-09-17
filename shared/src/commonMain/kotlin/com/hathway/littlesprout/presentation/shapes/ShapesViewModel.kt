package com.hathway.littlesprout.presentation.shapes

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.ShapeItem
import com.hathway.littlesprout.presentation.music.getAudioPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class ShapesViewModel : ViewModel() {
    private val audioPlayer = getAudioPlayer()

    private val _shapes = MutableStateFlow(
        listOf(
            ShapeItem("Circle", Res.drawable.shape_circle, "audio_circle.mp3"),
            ShapeItem("Square", Res.drawable.shape_square, "audio_square.mp3"),
            ShapeItem("Triangle", Res.drawable.shape_triangle, "audio_triangle.mp3"),
            ShapeItem("Rectangle", Res.drawable.shape_rectangle, "audio_rectangle.mp3"),
            ShapeItem("Oval", Res.drawable.shape_oval, "audio_oval.mp3"),
            ShapeItem("Star", Res.drawable.shape_star, "audio_star.mp3"),
            ShapeItem("Heart", Res.drawable.shape_heart, "audio_heart.mp3"),
            ShapeItem("Diamond", Res.drawable.shape_diamond, "audio_diamond.mp3"),
            ShapeItem("Pentagon", Res.drawable.shape_pentagon, "audio_pentagon.mp3"),
            ShapeItem("Hexagon", Res.drawable.shape_hexagon, "audio_hexagon.mp3"),
        )
    )
    val shapes = _shapes.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    init {
        audioPlayer.preload(_shapes.value.mapNotNull { it.soundRes })
        audioPlayer.onPlaybackComplete {
            _isPlaying.value = false
        }
    }

    fun playCurrentAudio() {
        if (_isPlaying.value) {
            stopAudio()
            return
        }
        val current = _shapes.value.getOrNull(_currentIndex.value)
        current?.soundRes?.let {
            _isPlaying.value = true
            audioPlayer.play(it)
        }
    }

    fun nextShape() {
        stopAudio()
        if (_currentIndex.value < _shapes.value.size - 1) {
            _currentIndex.value++
            playCurrentAudio()
        }
    }

    fun previousShape() {
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
