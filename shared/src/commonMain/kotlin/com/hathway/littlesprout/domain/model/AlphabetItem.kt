package com.hathway.littlesprout.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class AlphabetItem(
    val letter: String,
    val letterImage: DrawableResource,
    val objectImage: DrawableResource,
    val description: String,
    val soundRes: String? = null, // For future sound implementation
    val audio : String? = null
)