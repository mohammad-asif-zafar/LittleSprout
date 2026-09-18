package com.hathway.littlesprout.util

interface LinkLauncher {
    fun openUrl(url: String)
    fun sendEmail(address: String, subject: String = "", body: String = "")
}

expect fun getLinkLauncher(context: Any? = null): LinkLauncher

interface AppInfo {
    val version: String
    val buildNumber: String
}

expect fun getAppInfo(context: Any? = null): AppInfo
