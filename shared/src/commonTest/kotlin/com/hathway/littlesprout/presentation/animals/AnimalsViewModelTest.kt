package com.hathway.littlesprout.presentation.animals

import com.hathway.littlesprout.fakes.FakeAudioPlayer
import com.hathway.littlesprout.fakes.FakeProgressRepository
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
class AnimalsViewModelTest {
    private lateinit var viewModel: AnimalsViewModel
    private lateinit var audioPlayer: FakeAudioPlayer
    private lateinit var progressRepo: FakeProgressRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        audioPlayer = FakeAudioPlayer()
        progressRepo = FakeProgressRepository()
        viewModel = AnimalsViewModel(progressRepo, audioPlayer)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testNextAnimalNavigatesAndPlaysAudio() = runTest {
        audioPlayer.playedFiles.clear() // Clear the initial payload from init block
        
        viewModel.nextAnimal()
        advanceUntilIdle()
        
        assertEquals(1, viewModel.currentIndex.value)
        assertTrue(audioPlayer.playedFiles.isNotEmpty())
    }

    @Test
    fun testPreviousAnimalLimitsAtZero() = runTest {
        viewModel.previousAnimal()
        advanceUntilIdle()
        
        assertEquals(0, viewModel.currentIndex.value)
    }

    @Test
    fun testNextAnimalBoundaryAndCompletionTracking() = runTest {
        // Iterate through 8 animals (index 0 to 7)
        repeat(7) { viewModel.nextAnimal() }
        assertEquals(7, viewModel.currentIndex.value)
        
        viewModel.nextAnimal()
        advanceUntilIdle()
        
        val progress = progressRepo.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "ANIMALS_COMPLETE" && it.completionCount > 0 })
    }
}
