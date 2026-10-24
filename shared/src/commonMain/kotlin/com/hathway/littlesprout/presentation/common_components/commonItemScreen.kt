package com.hathway.littlesprout.presentation.common_components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CommonItemScreen(
    viewModel: CommonViewModel,
    category: String,
    items: List<CommonItem>,
    currentIndex: Int,
    isPlaying: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlaySoundClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val currentItem = items.getOrNull(currentIndex) ?: return
    val completedCategory by viewModel.completedCategory.collectAsState()

    CommonContent(
        currentItem = currentItem,
        isPreviousEnabled = currentIndex > 0,
        isNextEnabled = true,
        isPlaying = isPlaying,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onPreviousClick = onPreviousClick,
        onNextClick = onNextClick,
        onPlaySoundClick = onPlaySoundClick
    )

    if (completedCategory == category) {
        CompletionDialog(
            categoryName = category.lowercase(),
            totalCount = items.size,
            onPlayAgain = { viewModel.restartCategory(category) },
            onHome = {
                viewModel.restartCategory(category)
                onHomeClick()
            }
        )
    }
}
