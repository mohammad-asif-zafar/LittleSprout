package com.hathway.littlesprout.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class ColorItem(
    val name: String,
    val colorImage: DrawableResource,
    val colorCode: Long, // Hex color for the first letter
    val soundRes: String? = null
)