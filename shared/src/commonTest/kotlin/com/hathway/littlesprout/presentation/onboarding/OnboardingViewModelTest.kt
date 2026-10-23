package com.hathway.littlesprout.presentation.onboarding

import kotlin.test.Test
import kotlin.test.assertEquals

class OnboardingViewModelTest {

    @Test
    fun `pages contains exactly 3 onboarding items with expected titles`() {
        val viewModel = OnboardingViewModel()
        
        val pages = viewModel.pages.value
        assertEquals(3, pages.size, "Should contain exactly 3 onboarding pages")
        
        // Assert Integrity of State defaults
        assertEquals("A brighter tomorrow", pages[0].title)
        assertEquals("Fun Learning Activities", pages[1].title)
        assertEquals("A Safe & Ad-Free Environment", pages[2].title)
    }
}
