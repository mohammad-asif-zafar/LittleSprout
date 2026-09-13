package com.hathway.littlesprout.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class SongItem(
    val title: String,
    val icon: DrawableResource,
    val backgroundColor: Long, // Hex color for the list item
    val audioRes: String? = null
)