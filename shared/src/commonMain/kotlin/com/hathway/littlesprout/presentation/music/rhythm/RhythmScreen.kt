package com.hathway.littlesprout.presentation.music.rhythm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.hathway.littlesprout.util.LockScreenOrientation
import com.hathway.littlesprout.util.ScreenOrientation

@Composable
fun RhythmScreen(
    viewModel: RhythmViewModel,
    onBackClick: () -> Unit
) {
    LockScreenOrientation(ScreenOrientation.LANDSCAPE)

    val currentMode by viewModel.currentMode.collectAsState()
    val isMuted by viewModel.isMuted.collectAsState()
    val activePadId by viewModel.activePadId.collectAsState()
    val isShowingDemo by viewModel.isShowingDemo.collectAsState()
    val copyStep by viewModel.copyStep.collectAsState()
    val demoHighlightedSound by viewModel.demoHighlightedSound.collectAsState()
    val currentTrackName = viewModel.currentTrackDisplayName

    DisposableEffect(Unit) {
        viewModel.onScreenEntered()
        onDispose {
            viewModel.onScreenExited()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> {
                    viewModel.pauseAudio()
                }
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.resumeAudioIfNeeded()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    RhythmContent(
        currentMode = currentMode,
        isMuted = isMuted,
        activePadId = activePadId,
        isShowingDemo = isShowingDemo,
        copyStep = copyStep,
        copySequence = viewModel.copySequence,
        demoHighlightedSound = demoHighlightedSound,
        currentTrackName = currentTrackName,
        onModeSelect = { mode -> viewModel.setMode(mode) },
        onNextTrack = { viewModel.nextDanceTrack() },
        onDrumPress = { sound, padId -> viewModel.playDrum(sound, padId) },
        onDrumRelease = { padId -> viewModel.releaseDrum(padId) },
        onHomeClick = {
            viewModel.onScreenExited()
            onBackClick()
        },
        onToggleMute = { viewModel.toggleMute() }
    )
}
