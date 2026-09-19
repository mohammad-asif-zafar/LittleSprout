package com.hathway.littlesprout.presentation.parents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.model.AppSettings
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    val settings: StateFlow<AppSettings> = settingsRepository.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppSettings())

    fun updateSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateSettings(settings.value.copy(soundEnabled = enabled))
        }
    }

    fun updateMusicEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateSettings(settings.value.copy(musicEnabled = enabled))
        }
    }

    fun updateAutoPlayEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateSettings(settings.value.copy(autoPlayEnabled = enabled))
        }
    }

    fun updateQuietModeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateSettings(settings.value.copy(quietModeEnabled = enabled))
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            settingsRepository.updateSettings(settings.value.copy(isOnboardingCompleted = true))
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            progressRepository.resetProgress()
        }
    }
}
