package com.hathway.littlesprout.presentation.colors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.model.AppSettings
import com.hathway.littlesprout.domain.model.ColorItem
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

class ColorsViewModel(
    private val progressRepository: ProgressRepository? = null,
    private val settingsRepository: SettingsRepository? = null
) : ViewModel() {
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

    private val appSettings: StateFlow<AppSettings> = settingsRepository?.settings
        ?.stateIn(viewModelScope, SharingStarted.Eagerly, AppSettings())
        ?: MutableStateFlow(AppSettings())

    init {
        audioPlayer.preload(_colors.value.mapNotNull { it.soundRes })
        audioPlayer.onPlaybackComplete {
            _isPlaying.value = false
        }
    }

    fun playCurrentAudio(isAutoPlay: Boolean = false) {
        if (isAutoPlay && !appSettings.value.autoPlayEnabled) return
        if (!appSettings.value.soundEnabled) return
        if (appSettings.value.quietModeEnabled) return

        val current = _colors.value.getOrNull(_currentIndex.value)
        current?.soundRes?.let {
            _isPlaying.value = true
            audioPlayer.play(it, interruptCurrent = true)
            recordProgress(current.name, false)
        }
    }

    fun nextColor() {
        stopAudio()
        if (_currentIndex.value < _colors.value.size - 1) {
            _currentIndex.value++
            playCurrentAudio(isAutoPlay = true)
        } else {
            recordProgress("COLORS_COMPLETE", true)
        }
    }

    fun previousColor() {
        stopAudio()
        if (_currentIndex.value > 0) {
            _currentIndex.value--
            playCurrentAudio(isAutoPlay = true)
        }
    }

    private fun recordProgress(activityId: String, completed: Boolean) {
        viewModelScope.launch {
            progressRepository?.recordActivity(activityId, CategoryConstants.COLORS, completed)
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
