package com.hathway.littlesprout.presentation.alphabet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

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
        onNextClick = { viewModel.nextItem() },
        isPlaying = ,
        onPlaySoundClick = ,
    )
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
