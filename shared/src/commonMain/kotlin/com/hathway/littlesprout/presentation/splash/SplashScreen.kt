package com.hathway.littlesprout.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.img_splash
import org.jetbrains.compose.resources.painterResource

// 1. STATEFUL WRAPPER: Handles your ViewModel delay/ready lifecycle states safely
@Composable
fun SplashScreen(
    viewModel: SplashViewModel, onSplashFinished: () -> Unit
) {
    val isReady by viewModel.isReady.collectAsState()

    LaunchedEffect(isReady) {
        if (isReady) {
            onSplashFinished()
        }
    }

    SplashContent()
}

// 2. STATELESS CONTENT: Contains only static drawing logic (Safe for preview engine)
@Composable
fun SplashContent() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.img_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashContent()
    }
}
