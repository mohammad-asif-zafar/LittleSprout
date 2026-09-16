package com.hathway.littlesprout.presentation.common_components

import androidx.compose.runtime.Composable

@Composable
fun CommonItemScreen(
    items: List<CommonItem>,
    currentIndex: Int,
    isPlaying: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlaySoundClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val currentItem = items.getOrNull(currentIndex) ?: return

    CommonContent(
        currentItem = currentItem,
        isPreviousEnabled = currentIndex > 0,
        isNextEnabled = currentIndex < items.size - 1,
        isPlaying = isPlaying,
        onBackClick = onBackClick,
        onPreviousClick = onPreviousClick,
        onNextClick = onNextClick,
        onPlaySoundClick = onPlaySoundClick
    )
}
