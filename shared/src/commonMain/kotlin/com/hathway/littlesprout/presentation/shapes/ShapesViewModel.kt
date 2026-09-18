package com.hathway.littlesprout.presentation.shapes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.model.AppSettings
import com.hathway.littlesprout.domain.model.ShapeItem
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.domain.repository.SettingsRepository
import com.hathway.littlesprout.presentation.music.getAudioPlayer
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import littlesprout.shared.generated.resources.*

class ShapesViewModel(
    private val progressRepository: ProgressRepository? = null,
    private val settingsRepository: SettingsRepository? = null
) : ViewModel() {
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

    private val appSettings: StateFlow<AppSettings> = settingsRepository?.settings
        ?.stateIn(viewModelScope, SharingStarted.Eagerly, AppSettings())
        ?: MutableStateFlow(AppSettings())

    init {
        audioPlayer.preload(_shapes.value.mapNotNull { it.soundRes })
        audioPlayer.onPlaybackComplete {
            _isPlaying.value = false
        }
    }

    fun playCurrentAudio(isAutoPlay: Boolean = false) {
        if (isAutoPlay && !appSettings.value.autoPlayEnabled) return
        if (!appSettings.value.soundEnabled) return
        if (appSettings.value.quietModeEnabled) return

        val current = _shapes.value.getOrNull(_currentIndex.value)
        current?.soundRes?.let {
            _isPlaying.value = true
            audioPlayer.play(it, interruptCurrent = true)
            recordProgress(current.name, false)
        }
    }

    fun nextShape() {
        stopAudio()
        if (_currentIndex.value < _shapes.value.size - 1) {
            _currentIndex.value++
            playCurrentAudio(isAutoPlay = true)
        } else {
            recordProgress("SHAPES_COMPLETE", true)
        }
    }

    fun previousShape() {
        stopAudio()
        if (_currentIndex.value > 0) {
            _currentIndex.value--
            playCurrentAudio(isAutoPlay = true)
        }
    }

    private fun recordProgress(activityId: String, completed: Boolean) {
        viewModelScope.launch {
            progressRepository?.recordActivity(activityId, CategoryConstants.SHAPES, completed)
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
