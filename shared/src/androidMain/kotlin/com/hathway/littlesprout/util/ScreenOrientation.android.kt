package com.hathway.littlesprout.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

@Composable
actual fun LockScreenOrientation(orientation: ScreenOrientation) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        val targetOrientation = when (orientation) {
            ScreenOrientation.LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            ScreenOrientation.PORTRAIT -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            ScreenOrientation.UNSPECIFIED -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        if (activity.requestedOrientation != targetOrientation) {
            activity.requestedOrientation = targetOrientation
        }

        onDispose {
            if (originalOrientation == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED ||
                originalOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ||
                originalOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            ) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else if (activity.requestedOrientation != originalOrientation) {
                activity.requestedOrientation = originalOrientation
            }
        }
    }
}
