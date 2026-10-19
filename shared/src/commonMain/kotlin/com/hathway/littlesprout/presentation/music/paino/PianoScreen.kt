package com.hathway.littlesprout.presentation.music.paino

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hathway.littlesprout.util.LockScreenOrientation
import com.hathway.littlesprout.util.ScreenOrientation

@Composable
fun PianoScreen(
    viewModel: PianoViewModel,
    onBackClick: () -> Unit
) {
    LockScreenOrientation(ScreenOrientation.LANDSCAPE)

    val isMuted by viewModel.isMuted.collectAsState()
    val activeNoteIndex by viewModel.activeNoteIndex.collectAsState()
    val isSongMode by viewModel.isSongMode.collectAsState()
    val songStep by viewModel.songStep.collectAsState()

    PianoContent(
        notes = viewModel.notes,
        isMuted = isMuted,
        activeNoteIndex = activeNoteIndex,
        isSongMode = isSongMode,
        songStep = songStep,
        songSequence = viewModel.songSequence,
        onNotePress = { index -> viewModel.playNote(index) },
        onNoteRelease = { index -> viewModel.releaseNote(index) },
        onHomeClick = onBackClick,
        onToggleMute = { viewModel.toggleMute() },
        onToggleSongMode = { viewModel.toggleSongMode() }
    )
}
