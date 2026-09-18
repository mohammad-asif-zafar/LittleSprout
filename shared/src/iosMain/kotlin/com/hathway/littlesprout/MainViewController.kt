package com.hathway.littlesprout

import androidx.compose.ui.window.ComposeUIViewController
import com.hathway.littlesprout.di.AppContainer

fun MainViewController() = ComposeUIViewController {
    val appContainer = androidx.compose.runtime.remember { AppContainer() }
    App(appContainer)
}
