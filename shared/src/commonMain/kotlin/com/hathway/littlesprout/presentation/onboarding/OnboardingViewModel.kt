package com.hathway.littlesprout.presentation.onboarding

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.OnboardingPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.img_onboarding_1
import littlesprout.shared.generated.resources.img_onboarding_2
import littlesprout.shared.generated.resources.img_onboarding_3

class OnboardingViewModel : ViewModel() {
    private val _pages = MutableStateFlow(
        listOf(
            OnboardingPage(
                title = "A brighter tomorrow",
                description = "one little step at a time.",
                image = Res.drawable.img_onboarding_1
            ),
            OnboardingPage(
                title = "Fun Learning Activities",
                description = "Explore animals, colors, numbers, sounds and more through play.",
                image = Res.drawable.img_onboarding_2
            ),
            OnboardingPage(
                title = "A Safe & Ad-Free Environment",
                description = "Designed for young children with parental controls and peace of mind.",
                image = Res.drawable.img_onboarding_3
            )
        )
    )
    val pages = _pages.asStateFlow()
}