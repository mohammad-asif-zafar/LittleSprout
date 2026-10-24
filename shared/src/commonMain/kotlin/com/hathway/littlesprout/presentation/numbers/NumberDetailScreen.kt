package com.hathway.littlesprout.presentation.numbers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hathway.littlesprout.presentation.common_components.CompletionDialog

@Composable
fun NumberDetailScreen(
    viewModel: NumbersViewModel, 
    onBackClick: () -> Unit, 
    onHomeClick: () -> Unit
) {
    val numbers by viewModel.numbers.collectAsState()
    val selectedIndex by viewModel.selectedNumberIndex.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isCompleted by viewModel.isCompleted.collectAsState()

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
        onPlaySoundClick = { viewModel.playCurrentAudio() }
    )

    if (isCompleted) {
        CompletionDialog(
            categoryName = "numbers",
            totalCount = numbers.size,
            onPlayAgain = { viewModel.restart() },
            onHome = {
                viewModel.clearSelection()
                onHomeClick()
            }
        )
    }
}
