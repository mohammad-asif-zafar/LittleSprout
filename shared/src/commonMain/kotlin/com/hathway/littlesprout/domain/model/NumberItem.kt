package com.hathway.littlesprout.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class NumberItem(
    val value: Int,
    val name: String,
    val gridImage: DrawableResource,
    val detailImage: DrawableResource,
    val fingerText: String,
    val soundRes: String? = null
)