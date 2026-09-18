package com.hathway.littlesprout.domain.model

data class AppSettings(
    val soundEnabled: Boolean = true,
    val musicEnabled: Boolean = true,
    val autoPlayEnabled: Boolean = true,
    val quietModeEnabled: Boolean = false
)
