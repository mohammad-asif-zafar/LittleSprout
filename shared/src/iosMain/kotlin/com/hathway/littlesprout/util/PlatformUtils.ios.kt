package com.hathway.littlesprout.util

import platform.Foundation.NSBundle
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IosLinkLauncher : LinkLauncher {
    override fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl != null) {
            UIApplication.sharedApplication.openURL(nsUrl)
        }
    }

    override fun sendEmail(address: String, subject: String, body: String) {
        val encodedSubject = subject.replace(" ", "%20")
        val encodedBody = body.replace(" ", "%20")
        val urlString = "mailto:$address?subject=$encodedSubject&body=$encodedBody"
        val nsUrl = NSURL.URLWithString(urlString)
        if (nsUrl != null) {
            UIApplication.sharedApplication.openURL(nsUrl)
        }
    }
}

actual fun getLinkLauncher(context: Any?): LinkLauncher = IosLinkLauncher()

class IosAppInfo : AppInfo {
    override val version: String
        get() = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "Unknown"
        
    override val buildNumber: String
        get() = NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String ?: "0"
}

actual fun getAppInfo(context: Any?): AppInfo = IosAppInfo()
