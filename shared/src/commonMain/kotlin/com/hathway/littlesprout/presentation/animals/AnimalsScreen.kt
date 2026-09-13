package com.hathway.littlesprout.presentation.animals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

// 1. STATEFUL WRAPPER: Observes production reactive data streams from your ViewModel architecture safely
@Composable
fun AnimalsScreen(
    viewModel: AnimalsViewModel, onBackClick: () -> Unit, onHomeClick: () -> Unit
) {
    val animals by viewModel.animals.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val currentItem = animals.getOrNull(currentIndex) ?: return

    AnimalsContent(
        currentItem = currentItem,
        currentIndex = currentIndex,
        totalItemsCount = animals.size,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onPreviousClick = { viewModel.previousAnimal() },
        onNextClick = { viewModel.nextAnimal() },
        onPlaySoundClick = { /* Play Animal Sound */ })
}