package com.hathway.littlesprout.presentation.music.drums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.domain.repository.SettingsRepository
import com.hathway.littlesprout.presentation.music.AudioPlayer
import com.hathway.littlesprout.presentation.music.getAudioPlayer
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DrumsViewModel(
    private val progressRepository: ProgressRepository? = null,
    private val settingsRepository: SettingsRepository? = null,
    injectedAudioPlayer: AudioPlayer? = null
) : ViewModel() {
    private val audioPlayer = injectedAudioPlayer ?: getAudioPlayer()

    private val _isMuted = MutableStateFlow(false)
    val isMuted = _isMuted.asStateFlow()

    private val _activePadId = MutableStateFlow<String?>(null)
    val activePadId = _activePadId.asStateFlow()

    // Guided Rhythm Sequence for toddlers
    val rhythmSequence = listOf(
        DrumSound.KICK,
        DrumSound.SNARE,
        DrumSound.KICK,
        DrumSound.CLAPS,
        DrumSound.OPEN_HAT
    )

    private val _isRhythmMode = MutableStateFlow(false)
    val isRhythmMode = _isRhythmMode.asStateFlow()

    private val _rhythmStep = MutableStateFlow(0)
    val rhythmStep = _rhythmStep.asStateFlow()

    init {
        audioPlayer.preload(DrumSound.entries.map { it.fileName })
    }

    fun playDrum(sound: DrumSound, padId: String) {
        _activePadId.value = padId

        if (!_isMuted.value) {
            audioPlayer.play(sound.fileName, interruptCurrent = false)
        }

        if (_isRhythmMode.value) {
            val expectedSound = rhythmSequence.getOrNull(_rhythmStep.value)
            if (expectedSound == sound) {
                if (_rhythmStep.value + 1 < rhythmSequence.size) {
                    _rhythmStep.value += 1
                } else {
                    _rhythmStep.value = 0
                    recordProgress("DRUMS_RHYTHM_COMPLETE", true)
                }
            }
        }

        recordProgress("DRUM_${sound.name}", false)
    }

    fun releaseDrum(padId: String) {
        if (_activePadId.value == padId) {
            _activePadId.value = null
        }
    }

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
    }

    fun toggleRhythmMode() {
        _isRhythmMode.value = !_isRhythmMode.value
        _rhythmStep.value = 0
    }

    private fun recordProgress(activityId: String, completed: Boolean) {
        viewModelScope.launch {
            progressRepository?.recordActivity(activityId, CategoryConstants.SONGS, completed)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.stop()
    }
}
