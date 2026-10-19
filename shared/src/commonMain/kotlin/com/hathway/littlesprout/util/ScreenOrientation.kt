package com.hathway.littlesprout.util

import androidx.compose.runtime.Composable

enum class ScreenOrientation {
    PORTRAIT,
    LANDSCAPE,
    UNSPECIFIED
}

@Composable
expect fun LockScreenOrientation(orientation: ScreenOrientation)
