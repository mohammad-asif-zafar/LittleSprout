package com.hathway.littlesprout.presentation.numbers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

// 1. STATEFUL WRAPPER: Interacts cleanly with your Navigation and Architecture
@Composable
fun NumbersScreen(
    viewModel: NumbersViewModel, onBackClick: () -> Unit, onNumberClick: (Int) -> Unit
) {
    val numbers by viewModel.numbers.collectAsState()

    NumbersContent(
        numbers = numbers,
        onBackClick = onBackClick,
        onNumberClick = onNumberClick,
        onMusicToggleClick = { /* Toggle Background Music */ })
}
