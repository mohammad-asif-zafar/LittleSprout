package com.hathway.littlesprout.presentation.alphabet

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.littlesprout.domain.model.AlphabetItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.aa
import littlesprout.shared.generated.resources.img_apple

// 1. STATEFUL WRAPPER: Used by your App navigation
@Composable
fun AlphabetScreen(
    viewModel: AlphabetViewModel, onBackClick: () -> Unit
) {
    val items by viewModel.alphabetList.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val currentItem = items.getOrNull(currentIndex) ?: return

    AlphabetContent(
        currentItem = currentItem,
        isPreviousEnabled = currentIndex > 0,
        isNextEnabled = currentIndex < items.size - 1,
        onBackClick = onBackClick,
        onPreviousClick = { viewModel.previousItem() },
        onNextClick = { viewModel.nextItem() })
}


// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun AlphabetScreenPreview() {
    MaterialTheme {
        // Supplying static mock data so the Layout Engine renders immediately without actual architecture errors
        val mockItem = AlphabetItem(
            letter = "Aa",
            letterImage = Res.drawable.aa,
            objectImage = Res.drawable.img_apple,
            description = "A for Apple",
            audio = "a_apple"
        )

        AlphabetContent(
            currentItem = mockItem,
            isPreviousEnabled = false, // Emulate first card logic
            isNextEnabled = true,
            onBackClick = {},
            onPreviousClick = {},
            onNextClick = {})
    }
}