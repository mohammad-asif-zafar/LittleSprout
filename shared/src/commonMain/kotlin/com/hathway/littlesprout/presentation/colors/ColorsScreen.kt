package com.hathway.littlesprout.presentation.colors

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.littlesprout.domain.model.ColorItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.img_apple

// 1. STATEFUL WRAPPER: Handles ViewModel architecture states
@Composable
fun ColorsScreen(
    viewModel: ColorsViewModel, onBackClick: () -> Unit, onHomeClick: () -> Unit
) {
    val colors by viewModel.colors.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentItem = colors.getOrNull(currentIndex) ?: return

    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.playCurrentAudio()
    }

    ColorsContent(
        currentItem = currentItem,
        currentIndex = currentIndex,
        totalItemsCount = colors.size,
        isPlaying = isPlaying,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onPreviousClick = { viewModel.previousColor() },
        onNextClick = { viewModel.nextColor() },
        onPlaySoundClick = { viewModel.playCurrentAudio() })
}

// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun ColorsScreenPreviewMain() {
    MaterialTheme {
        // Providing explicit layout state objects so it processes inside the IDE preview system flawlessly
        val mockColor = ColorItem(
            name = "Red",
            colorImage = Res.drawable.img_apple, // Fallback/temporary icon resource if splash assets are missing
            colorCode = 0xFFFF0000,              // Red Color hex representation
            soundRes = "red_sound"
        )

        ColorsContent(
            currentItem = mockColor,
            currentIndex = 1, // Simulates an active middle item so left & right arrow elements load up
            totalItemsCount = 3,
            isPlaying = false,
            onBackClick = {},
            onHomeClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onPlaySoundClick = {})
    }
}