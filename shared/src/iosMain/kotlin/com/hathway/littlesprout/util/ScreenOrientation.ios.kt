package com.hathway.littlesprout.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import platform.Foundation.NSNumber
import platform.Foundation.setValue
import platform.UIKit.UIDevice
import platform.UIKit.UIInterfaceOrientationLandscapeRight
import platform.UIKit.UIInterfaceOrientationPortrait
import platform.UIKit.UIInterfaceOrientationUnknown
import platform.darwin.NSObject

@Composable
actual fun LockScreenOrientation(orientation: ScreenOrientation) {
    DisposableEffect(orientation) {
        val value = when (orientation) {
            ScreenOrientation.LANDSCAPE -> UIInterfaceOrientationLandscapeRight
            ScreenOrientation.PORTRAIT -> UIInterfaceOrientationPortrait
            ScreenOrientation.UNSPECIFIED -> UIInterfaceOrientationUnknown
        }
        
        try {
            val device = UIDevice.currentDevice as? NSObject
            device?.setValue(NSNumber(long = value), forKey = "orientation")
        } catch (_: Throwable) {
            // Safe fallback
        }

        onDispose {
            try {
                val device = UIDevice.currentDevice as? NSObject
                device?.setValue(NSNumber(long = UIInterfaceOrientationPortrait), forKey = "orientation")
            } catch (_: Throwable) {
                // Safe fallback
            }
        }
    }
}
