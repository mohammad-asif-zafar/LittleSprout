package com.hathway.littlesprout.presentation.music.rhythm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.domain.repository.SettingsRepository
import com.hathway.littlesprout.presentation.music.drums.DrumSound
import com.hathway.littlesprout.presentation.music.AudioPlayer
import com.hathway.littlesprout.presentation.music.getAudioPlayer
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RhythmViewModel(
    private val progressRepository: ProgressRepository? = null,
    private val settingsRepository: SettingsRepository? = null,
    injectedAudioPlayer: AudioPlayer? = null
) : ViewModel() {
    private val audioPlayer = injectedAudioPlayer ?: getAudioPlayer()

    private val _currentMode = MutableStateFlow(RhythmMode.PLAY)
    val currentMode = _currentMode.asStateFlow()

    private val _isMuted = MutableStateFlow(false)
    val isMuted = _isMuted.asStateFlow()

    private val _activePadId = MutableStateFlow<String?>(null)
    val activePadId = _activePadId.asStateFlow()

    // 12 Dance tracks from mixkit
    val danceTracks = listOf(
        "orbidino_music_one.mp3",
        "orbidino_music_ten.mp3",
        "orbidino_music_two.mp3",
        "orbidino_music_eight.mp3",
        "orbidino_music_twelen.mp3",
        "orbidino_music_five.mp3",
        "orbidino_music_four.mp3",
        "orbidino_music_three.mp3",
        "orbidino_music_six.mp3",
        "orbidino_music_seven.mp3",
        "orbidino_music_nine.mp3",
        "orbidino_music_eleven.mp3"
    )

    private val _currentTrackIndex = MutableStateFlow(0)
    val currentTrackIndex = _currentTrackIndex.asStateFlow()

    val currentTrackDisplayName: String
        get() {
            val index = _currentTrackIndex.value
            return "Dance Track ${index + 1}"
        }

    // Copy Beat Game state
    val copySequence = listOf(DrumSound.KICK, DrumSound.SNARE, DrumSound.KICK, DrumSound.CLOSED_HAT)
    private val _copyStep = MutableStateFlow(0)
    val copyStep = _copyStep.asStateFlow()

    private val _isShowingDemo = MutableStateFlow(false)
    val isShowingDemo = _isShowingDemo.asStateFlow()

    private val _demoHighlightedSound = MutableStateFlow<DrumSound?>(null)
    val demoHighlightedSound = _demoHighlightedSound.asStateFlow()

    private var copyDemoJob: Job? = null
    private var isScreenActive = false

    init {
        try {
            audioPlayer.preload(DrumSound.entries.map { it.fileName })
            audioPlayer.preload(danceTracks)
        } catch (e: Exception) {
            // Ignore preload exceptions gracefully
        }
    }

    fun onScreenEntered() {
        isScreenActive = true
        if (_currentMode.value == RhythmMode.DANCE && !_isMuted.value) {
            startDanceTrack()
        }
    }

    fun onScreenExited() {
        isScreenActive = false
        stopAllAudio()
        _currentMode.value = RhythmMode.PLAY
    }

    fun pauseAudio() {
        try {
            audioPlayer.pause()
        } catch (e: Exception) {
            // Graceful handling
        }
    }

    fun resumeAudioIfNeeded() {
        if (isScreenActive && _currentMode.value == RhythmMode.DANCE && !_isMuted.value) {
            try {
                if (!audioPlayer.isPlaying()) {
                    startDanceTrack()
                } else {
                    audioPlayer.resume()
                }
            } catch (e: Exception) {
                // Graceful handling
            }
        }
    }

    fun setMode(mode: RhythmMode) {
        val prev = _currentMode.value
        _currentMode.value = mode

        if (prev == RhythmMode.DANCE && mode != RhythmMode.DANCE) {
            stopAllAudio()
        }

        if (mode == RhythmMode.COPY) {
            startCopyDemo()
        } else if (mode == RhythmMode.DANCE) {
            startDanceTrack()
            recordProgress("RHYTHM_DANCE_START", false)
        }
    }

    private fun startDanceTrack() {
        try {
            audioPlayer.stop()
            if (!_isMuted.value && isScreenActive && _currentMode.value == RhythmMode.DANCE) {
                audioPlayer.onPlaybackComplete {
                    viewModelScope.launch {
                        if (isScreenActive && _currentMode.value == RhythmMode.DANCE) {
                            nextDanceTrack()
                        }
                    }
                }
                val track = danceTracks.getOrNull(_currentTrackIndex.value) ?: danceTracks[0]
                audioPlayer.play(track, interruptCurrent = true)
            }
        } catch (e: Exception) {
            // Gracefully handle playback failure without crash
        }
    }

    fun nextDanceTrack() {
        _currentTrackIndex.value = (_currentTrackIndex.value + 1) % danceTracks.size
        if (_currentMode.value == RhythmMode.DANCE) {
            startDanceTrack()
        }
    }

    fun playDrum(sound: DrumSound, padId: String) {
        _activePadId.value = padId

        if (!_isMuted.value) {
            try {
                audioPlayer.play(sound.fileName, interruptCurrent = false)
            } catch (e: Exception) {
                // Gracefully handle drum sound failure
            }
        }

        if (_currentMode.value == RhythmMode.COPY && !_isShowingDemo.value) {
            val expected = copySequence.getOrNull(_copyStep.value)
            if (expected == sound) {
                if (_copyStep.value + 1 < copySequence.size) {
                    _copyStep.value += 1
                } else {
                    _copyStep.value = 0
                    recordProgress("RHYTHM_COPY_COMPLETE", true)
                    startCopyDemo()
                }
            }
        }

        recordProgress("RHYTHM_${sound.name}", false)
    }

    fun releaseDrum(padId: String) {
        if (_activePadId.value == padId) {
            _activePadId.value = null
        }
    }

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
        if (_isMuted.value) {
            stopAllAudio()
        } else if (_currentMode.value == RhythmMode.DANCE) {
            startDanceTrack()
        }
    }

    private fun stopAllAudio() {
        try {
            audioPlayer.stop()
            audioPlayer.onPlaybackComplete {}
        } catch (e: Exception) {
            // Graceful handling
        }
        copyDemoJob?.cancel()
        copyDemoJob = null
        _isShowingDemo.value = false
        _demoHighlightedSound.value = null
    }

    private fun startCopyDemo() {
        copyDemoJob?.cancel()
        copyDemoJob = viewModelScope.launch {
            _isShowingDemo.value = true
            _copyStep.value = 0
            delay(500)
            for (sound in copySequence) {
                if (_currentMode.value != RhythmMode.COPY || !isScreenActive) break
                _demoHighlightedSound.value = sound
                if (!_isMuted.value) {
                    try {
                        audioPlayer.play(sound.fileName, interruptCurrent = true)
                    } catch (e: Exception) {
                        // Graceful handling
                    }
                }
                delay(600)
                _demoHighlightedSound.value = null
                delay(300)
            }
            _isShowingDemo.value = false
        }
    }

    private fun recordProgress(activityId: String, completed: Boolean) {
        viewModelScope.launch {
            try {
                progressRepository?.recordActivity(activityId, CategoryConstants.SONGS, completed)
            } catch (e: Exception) {
                // Ignore repository errors
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopAllAudio()
        try {
            audioPlayer.release()
        } catch (e: Exception) {
            // Graceful handling
        }
    }
}

