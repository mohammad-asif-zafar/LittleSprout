package com.hathway.littlesprout.presentation.alphabet

import com.hathway.littlesprout.domain.model.AppSettings
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
class AlphabetViewModelTest {
    private lateinit var viewModel: AlphabetViewModel
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
        viewModel = AlphabetViewModel(progressRepo, settingsRepo, audioPlayer)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testNextItemNavigatesAndPlaysAudio() = runTest {
        viewModel.nextItem()
        advanceUntilIdle()
        
        assertEquals(1, viewModel.currentIndex.value)
        assertTrue(audioPlayer.playedFiles.isNotEmpty())
    }

    @Test
    fun testPreviousItemLimitsAtZero() = runTest {
        viewModel.previousItem()
        advanceUntilIdle()
        
        assertEquals(0, viewModel.currentIndex.value)
    }

    @Test
    fun testNextItemBoundaryAndCompletionTracking() = runTest {
        // Move through 26 letters (index 0 to 25)
        repeat(25) { viewModel.nextItem() }
        assertEquals(25, viewModel.currentIndex.value)
        
        // This triggers completion
        viewModel.nextItem()
        advanceUntilIdle()
        
        assertEquals(25, viewModel.currentIndex.value) // Boundary limit respected
        val progress = progressRepo.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "ALPHABET_COMPLETE" && it.completionCount > 0 })
    }

    @Test
    fun testAutoPlaySettingsRespected() = runTest {
        settingsRepo.updateSettings(AppSettings(autoPlayEnabled = false))
        advanceUntilIdle()
        audioPlayer.playedFiles.clear()
        
        viewModel.nextItem() // Attempts to autoPlay
        advanceUntilIdle()
        
        // Playback should be blocked by setting
        assertTrue(audioPlayer.playedFiles.isEmpty())
    }
}
