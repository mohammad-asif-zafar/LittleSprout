package com.hathway.littlesprout.presentation.alphabet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hathway.littlesprout.presentation.common_components.CompletionDialog

@Composable
fun AlphabetScreen(
    viewModel: AlphabetViewModel,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val items by viewModel.alphabetList.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isCompleted by viewModel.isCompleted.collectAsState()
    val currentItem = items.getOrNull(currentIndex) ?: return

    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.playCurrentAudio()
    }

    AlphabetContent(
        currentItem = currentItem,
        isPreviousEnabled = currentIndex > 0,
        isNextEnabled = true,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onPreviousClick = { viewModel.previousItem() },
        onNextClick = { viewModel.nextItem() },
        isPlaying = isPlaying,
        onPlaySoundClick = { viewModel.playCurrentAudio() },
    )

    if (isCompleted) {
        CompletionDialog(
            categoryName = "letters",
            totalCount = items.size,
            onPlayAgain = { viewModel.restart() },
            onHome = {
                viewModel.restart()
                onHomeClick()
            }
        )
    }
}



/*
AlphabetContent(
currentItem = currentItem,
isPreviousEnabled = currentIndex > 0,
isNextEnabled = currentIndex < items.size - 1,
isPlaying = isPlaying,
onBackClick = onBackClick,
onPreviousClick =  { viewModel.previousItem() ,
onNextClick = onNextClick,
onPlaySoundClick = { viewModel.nextItem() })
}*/
