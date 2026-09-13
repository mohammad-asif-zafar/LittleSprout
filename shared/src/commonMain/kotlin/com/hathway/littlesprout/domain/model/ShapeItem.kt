package com.hathway.littlesprout.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class ShapeItem(
    val name: String,
    val shapeImage: DrawableResource,
    val soundRes: String? = null
)