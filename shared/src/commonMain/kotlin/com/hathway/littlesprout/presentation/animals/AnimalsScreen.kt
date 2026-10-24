package com.hathway.littlesprout.presentation.animals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hathway.littlesprout.presentation.common_components.CompletionDialog

@Composable
fun AnimalsScreen(
    viewModel: AnimalsViewModel, 
    onBackClick: () -> Unit, 
    onHomeClick: () -> Unit
) {
    val animals by viewModel.animals.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isCompleted by viewModel.isCompleted.collectAsState()
    val currentItem = animals.getOrNull(currentIndex) ?: return

    AnimalsContent(
        currentItem = currentItem,
        currentIndex = currentIndex,
        totalItemsCount = animals.size,
        isPlaying = isPlaying,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onPreviousClick = { viewModel.previousAnimal() },
        onNextClick = { viewModel.nextAnimal() },
        onPlaySoundClick = { viewModel.playAnimalSound() }
    )

    if (isCompleted) {
        CompletionDialog(
            categoryName = "animals",
            totalCount = animals.size,
            onPlayAgain = { viewModel.restart() },
            onHome = {
                viewModel.restart()
                onHomeClick()
            }
        )
    }
}
