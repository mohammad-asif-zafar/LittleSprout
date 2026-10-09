package com.hathway.littlesprout

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import com.hathway.littlesprout.di.AppContainer
import com.hathway.littlesprout.presentation.music.AudioPlayerFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        
        AudioPlayerFactory.context = applicationContext

        setContent {
            val appContainer = remember { AppContainer(applicationContext) }
            App(appContainer)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}