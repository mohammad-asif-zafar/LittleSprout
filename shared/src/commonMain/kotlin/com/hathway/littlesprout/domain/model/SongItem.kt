package com.hathway.littlesprout.domain.model

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

data class SongItem(
    val title: String,
    val lyrics: String,
    val imageRes: DrawableResource, // Matches your Res.drawable pattern
    val backgroundColorLong: Long, // Captures hex color literals (e.g., 0xFFE1F5FE)
    val audioPath: String
) {
    // Helper property to easily transform the raw Long into a Compose Color instance
    val backgroundColor: Color get() = Color(backgroundColorLong)
}