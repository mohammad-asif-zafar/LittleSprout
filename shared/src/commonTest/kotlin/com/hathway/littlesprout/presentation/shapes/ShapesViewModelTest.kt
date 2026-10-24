package com.hathway.littlesprout.presentation.shapes

import com.hathway.littlesprout.fakes.FakeAudioPlayer
import com.hathway.littlesprout.fakes.FakeProgressRepository
import com.hathway.littlesprout.fakes.FakeSettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ShapesViewModelTest {
    private lateinit var viewModel: ShapesViewModel
    private lateinit var audioPlayer: FakeAudioPlayer
    private lateinit var progressRepo: FakeProgressRepository
    private lateinit var settingsRepo: FakeSettingsRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        audioPlayer = FakeAudioPlayer()
        progressRepo = FakeProgressRepository()
        settingsRepo = FakeSettingsRepository()
        viewModel = ShapesViewModel(progressRepo, settingsRepo, audioPlayer)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testNextShapeNavigatesAndPlaysAudio() = runTest {
        viewModel.nextShape()
        advanceUntilIdle()
        
        assertEquals(1, viewModel.currentIndex.value)
        assertTrue(audioPlayer.playedFiles.isNotEmpty())
    }

    @Test
    fun testPreviousShapeLimitsAtZero() = runTest {
        viewModel.previousShape()
        advanceUntilIdle()
        
        assertEquals(0, viewModel.currentIndex.value)
    }

    @Test
    fun testNextShapeBoundaryAndCompletionTracking() = runTest {
        // Iterate through 10 shapes (index 0 to 9)
        repeat(9) { viewModel.nextShape() }
        assertEquals(9, viewModel.currentIndex.value)
        
        viewModel.nextShape()
        advanceUntilIdle()
        
        val progress = progressRepo.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "SHAPES_COMPLETE" && it.completionCount > 0 })
    }
}
