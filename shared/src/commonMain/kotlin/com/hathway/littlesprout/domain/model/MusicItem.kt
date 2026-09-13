package com.hathway.littlesprout.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class MusicItem(
    val name: String,
    val image: DrawableResource,
    val type: MusicType
)

enum class MusicType {
    SING_ALONG, PIANO, DRUMS, RHYTHM
}