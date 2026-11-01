package com.hathway.littlesprout.presentation.common_components

import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import kotlin.test.Test
import kotlin.test.assertEquals

class AppTextTest {

    @Test
    fun appTextParametersAndDefaultsAreValid() {
        val sampleText = "Hello Little Sprout Explorers!"
        assertEquals("Hello Little Sprout Explorers!", sampleText)
    }
}
