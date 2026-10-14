package com.hathway.littlesprout.presentation.music.paino

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.domain.repository.SettingsRepository
import com.hathway.littlesprout.presentation.music.getAudioPlayer
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import littlesprout.shared.generated.resources.*

class PianoViewModel(
    private val progressRepository: ProgressRepository? = null,
    private val settingsRepository: SettingsRepository? = null
) : ViewModel() {
    private val audioPlayer = getAudioPlayer()

    val notes = listOf(
        PianoNote("C", "Do", "piano_c.wav", Color(0xFFFF3B30), Res.drawable.paino_red),
        PianoNote("D", "Re", "piano_d.wav", Color(0xFFFF9500), Res.drawable.paino_oranage),
        PianoNote("E", "Mi", "piano_e.wav", Color(0xFFFFCC00), Res.drawable.paino_yellow),
        PianoNote("F", "Fa", "piano_f.wav", Color(0xFF34C759), Res.drawable.paino_green),
        PianoNote("G", "Sol", "piano_g.wav", Color(0xFF007AFF), Res.drawable.paino_blue),
        PianoNote("A", "La", "piano_a.wav", Color(0xFFAF52DE), Res.drawable.paino_voilet),
        PianoNote("B", "Ti", "piano_b.wav", Color(0xFFFF2D55), Res.drawable.paino_red)
    )

    private val _isMuted = MutableStateFlow(false)
    val isMuted = _isMuted.asStateFlow()

    private val _activeNoteIndex = MutableStateFlow<Int?>(null)
    val activeNoteIndex = _activeNoteIndex.asStateFlow()

    // Simple Song Mode (Twinkle Twinkle Little Star sequence)
    val songSequence = listOf(0, 0, 4, 4, 5, 5, 4, 3, 3, 2, 2, 1, 1, 0)
    
    private val _isSongMode = MutableStateFlow(false)
    val isSongMode = _isSongMode.asStateFlow()

    private val _songStep = MutableStateFlow(0)
    val songStep = _songStep.asStateFlow()

    init {
        audioPlayer.preload(notes.map { it.soundRes })
    }

    fun playNote(index: Int) {
        val note = notes.getOrNull(index) ?: return
        _activeNoteIndex.value = index

        if (!_isMuted.value) {
            audioPlayer.play(note.soundRes, interruptCurrent = false)
        }

        if (_isSongMode.value) {
            val expectedIndex = songSequence.getOrNull(_songStep.value)
            if (expectedIndex == index) {
                if (_songStep.value + 1 < songSequence.size) {
                    _songStep.value += 1
                } else {
                    _songStep.value = 0
                    recordProgress("PIANO_SONG_COMPLETE", true)
                }
            }
        }

        recordProgress("PIANO_NOTE_${note.note}", false)
    }

    fun releaseNote(index: Int) {
        if (_activeNoteIndex.value == index) {
            _activeNoteIndex.value = null
        }
    }

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
    }

    fun toggleSongMode() {
        _isSongMode.value = !_isSongMode.value
        _songStep.value = 0
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
