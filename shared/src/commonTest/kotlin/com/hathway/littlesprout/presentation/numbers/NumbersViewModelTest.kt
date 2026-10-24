package com.hathway.littlesprout.presentation.numbers

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
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NumbersViewModelTest {
    private lateinit var viewModel: NumbersViewModel
    private lateinit var audioPlayer: FakeAudioPlayer
    private lateinit var progressRepo: FakeProgressRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        audioPlayer = FakeAudioPlayer()
        progressRepo = FakeProgressRepository()
        viewModel = NumbersViewModel(progressRepo, audioPlayer)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSelectNumberPlaysAudioAndUpdateState() = runTest {
        viewModel.selectNumber(3)
        advanceUntilIdle()
        
        assertEquals(3, viewModel.selectedNumberIndex.value)
        assertTrue(audioPlayer.playedFiles.isNotEmpty())
    }

    @Test
    fun testClearSelectionReturnsToGrid() = runTest {
        viewModel.selectNumber(2)
        assertEquals(2, viewModel.selectedNumberIndex.value)
        
        viewModel.clearSelection()
        assertNull(viewModel.selectedNumberIndex.value)
    }

    @Test
    fun testNextNumberBoundsAndCompletion() = runTest {
        viewModel.selectNumber(8) // Index 8 corresponds to 9
        viewModel.nextNumber()
        assertEquals(9, viewModel.selectedNumberIndex.value)
        
        viewModel.nextNumber() // Boundary (10th item). Triggers complete instead
        advanceUntilIdle()
        
        assertEquals(9, viewModel.selectedNumberIndex.value)
        val progress = progressRepo.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "NUMBERS_COMPLETE" && it.completionCount > 0 })
    }

    @Test
    fun testPreviousNumberBounds() = runTest {
        viewModel.selectNumber(1)
        viewModel.previousNumber()
        assertEquals(0, viewModel.selectedNumberIndex.value)
        
        viewModel.previousNumber()
        assertEquals(0, viewModel.selectedNumberIndex.value) // Zero-bound enforced
    }
}
