package com.hathway.littlesprout.presentation.music.paino

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

data class PianoNote(
    val note: String,
    val solfege: String,
    val soundRes: String,
    val keyColor: Color,
    val keyDrawable: DrawableResource? = null
)
