package com.hathway.littlesprout.presentation.shapes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

// 1. STATEFUL WRAPPER: Connects directly with your ViewModel architecture streams
@Composable
fun ShapesScreen(
    viewModel: ShapesViewModel, onBackClick: () -> Unit, onHomeClick: () -> Unit
) {
    val shapes by viewModel.shapes.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentItem = shapes.getOrNull(currentIndex) ?: return

    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.playCurrentAudio()
    }

    ShapesContent(
        currentItem = currentItem,
        currentIndex = currentIndex,
        totalItemsCount = shapes.size,
        isPlaying = isPlaying,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onPreviousClick = { viewModel.previousShape() },
        onNextClick = { viewModel.nextShape() },
        onPlaySoundClick = { viewModel.playCurrentAudio() })
}
