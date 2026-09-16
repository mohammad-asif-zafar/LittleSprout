package com.hathway.littlesprout.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class AnimalItem(
    val name: String,
    val image: DrawableResource,
    val audio: String? = null
)
