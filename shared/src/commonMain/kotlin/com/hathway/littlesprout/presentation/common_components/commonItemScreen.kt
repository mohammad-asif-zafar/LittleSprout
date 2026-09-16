package com.hathway.littlesprout.presentation.common_components


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CommonItemScreen(
    items: List<CommonItem>,
    currentIndex: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val currentItem = items.getOrNull(currentIndex) ?: return

    CommonContent(
        currentItem = currentItem,
        isPreviousEnabled = currentIndex > 0,
        isNextEnabled = currentIndex < items.size - 1,
        onBackClick = onBackClick,
        onPreviousClick = onPreviousClick,
        onNextClick = onNextClick
    )
}
