package com.hathway.littlesprout.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class OnboardingPage(
    val title: String,
    val description: String,
    val image: DrawableResource
)