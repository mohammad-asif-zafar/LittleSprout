package com.hathway.littlesprout.presentation.splash

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun isReady_is_false_initially_and_becomes_true_after_2000ms() = runTest {
        val viewModel = SplashViewModel()

        // Wait to make sure default value is maintained before the delay finishes
        assertFalse(viewModel.isReady.value)

        // Advance close to threshold
        advanceTimeBy(1000.milliseconds)
        assertFalse(viewModel.isReady.value)

        // Cross the 2000ms threshold completely
        advanceTimeBy(1001.milliseconds)
        assertTrue(viewModel.isReady.value)
    }
}
