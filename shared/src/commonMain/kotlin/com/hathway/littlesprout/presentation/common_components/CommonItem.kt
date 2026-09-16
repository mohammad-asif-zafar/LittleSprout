package com.hathway.littlesprout.presentation.common_components

import org.jetbrains.compose.resources.DrawableResource

data class CommonItem(
    val letter: String,
    val letterImage: DrawableResource,
    val objectImage: DrawableResource,
    val description: String,
    val soundRes: String? = null,
    val audio: String? = null
)
