package com.hathway.littlesprout.presentation.music.drums

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hathway.littlesprout.util.LockScreenOrientation
import com.hathway.littlesprout.util.ScreenOrientation

@Composable
fun DrumsScreen(
    viewModel: DrumsViewModel,
    onBackClick: () -> Unit
) {
    LockScreenOrientation(ScreenOrientation.LANDSCAPE)

    val isMuted by viewModel.isMuted.collectAsState()
    val activePadId by viewModel.activePadId.collectAsState()
    val isRhythmMode by viewModel.isRhythmMode.collectAsState()
    val rhythmStep by viewModel.rhythmStep.collectAsState()

    DrumsContent(
        isMuted = isMuted,
        activePadId = activePadId,
        isRhythmMode = isRhythmMode,
        rhythmStep = rhythmStep,
        rhythmSequence = viewModel.rhythmSequence,
        onDrumPress = { sound, padId -> viewModel.playDrum(sound, padId) },
        onDrumRelease = { padId -> viewModel.releaseDrum(padId) },
        onHomeClick = onBackClick,
        onToggleMute = { viewModel.toggleMute() },
        onToggleRhythmMode = { viewModel.toggleRhythmMode() }
    )
}
