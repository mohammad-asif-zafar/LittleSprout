package com.hathway.littlesprout.presentation.numbers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

// 1. STATEFUL WRAPPER: Resolves state logic safely from your ViewModel architecture
@Composable
fun NumberDetailScreen(
    viewModel: NumbersViewModel, 
    onBackClick: () -> Unit, 
    onHomeClick: () -> Unit
) {
    val numbers by viewModel.numbers.collectAsState()
    val selectedIndex by viewModel.selectedNumberIndex.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    val currentItem = selectedIndex?.let { numbers.getOrNull(it) } ?: return

    LaunchedEffect(currentItem) {
        viewModel.playCurrentAudio()
    }

    NumberDetailContent(
        currentItem = currentItem,
        selectedIndex = selectedIndex ?: 0,
        totalItemsCount = numbers.size,
        isPlaying = isPlaying,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onPreviousClick = { viewModel.previousNumber() },
        onNextClick = { viewModel.nextNumber() },
        onPlaySoundClick = { viewModel.playCurrentAudio() })
}
