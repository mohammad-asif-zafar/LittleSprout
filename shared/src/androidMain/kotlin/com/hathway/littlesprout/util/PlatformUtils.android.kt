package com.hathway.littlesprout.util

import android.content.Context
import android.content.Intent
import android.net.Uri

import com.hathway.littlesprout.presentation.music.AudioPlayerFactory

class AndroidLinkLauncher(private val context: Context) : LinkLauncher {
    override fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    override fun sendEmail(address: String, subject: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}

actual fun getLinkLauncher(context: Any?): LinkLauncher {
    val ctx = (context as? Context) ?: try {
        AudioPlayerFactory.context
    } catch (e: Exception) {
        null
    }
    
    return if (ctx != null) {
        AndroidLinkLauncher(ctx)
    } else {
        // Fallback for cases where context is not available (like previews)
        object : LinkLauncher {
            override fun openUrl(url: String) {}
            override fun sendEmail(address: String, subject: String, body: String) {}
        }
    }
}

class AndroidAppInfo(private val context: Context) : AppInfo {
    override val version: String
        get() = try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
        
    override val buildNumber: String
        get() = try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionCode.toString()
        } catch (e: Exception) {
            "0"
        }
}

actual fun getAppInfo(context: Any?): AppInfo {
    val ctx = (context as? Context) ?: try {
        AudioPlayerFactory.context
    } catch (e: Exception) {
        null
    }
    
    return if (ctx != null) {
        AndroidAppInfo(ctx)
    } else {
        object : AppInfo {
            override val version: String = "Unknown"
            override val buildNumber: String = "0"
        }
    }
}
