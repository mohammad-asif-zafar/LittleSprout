package com.hathway.littlesprout.presentation.common_components


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

// 1. STATEFUL WRAPPER: Used by your App navigation
@Composable
fun CommonItemScreen(
    viewModel: CommonViewModel, onBackClick: () -> Unit
) {
    val itemsBirds by viewModel.birdList.collectAsState()
    val currentBirdIIndex by viewModel.currentBirdIIndex.collectAsState()
    val currentBirdsItem = itemsBirds.getOrNull(currentBirdIIndex) ?: return

    CommonContent(
        currentItem = currentBirdsItem,
        isPreviousEnabled = currentBirdIIndex > 0,
        isNextEnabled = currentBirdIIndex < itemsBirds.size - 1,
        onBackClick = onBackClick,
        onPreviousClick = { viewModel.previousBirdIItem() },
        onNextClick = { viewModel.nextBirdItem() })
}
