package com.hathway.littlesprout.presentation.colors

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
class ColorsViewModelTest {
    private lateinit var viewModel: ColorsViewModel
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
        viewModel = ColorsViewModel(progressRepo, settingsRepo, audioPlayer)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testNextColorNavigatesAndPlaysAudio() = runTest {
        viewModel.nextColor()
        advanceUntilIdle()
        
        assertEquals(1, viewModel.currentIndex.value)
        assertTrue(audioPlayer.playedFiles.isNotEmpty())
    }

    @Test
    fun testPreviousColorLimitsAtZero() = runTest {
        viewModel.previousColor()
        advanceUntilIdle()
        
        assertEquals(0, viewModel.currentIndex.value)
    }

    @Test
    fun testNextColorBoundaryAndCompletionTracking() = runTest {
        // Iterate through 8 colors (index 0 to 7)
        repeat(7) { viewModel.nextColor() }
        assertEquals(7, viewModel.currentIndex.value)
        
        // Exceed limit -> Trigger complete
        viewModel.nextColor()
        advanceUntilIdle()
        
        assertEquals(7, viewModel.currentIndex.value)
        val progress = progressRepo.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "COLORS_COMPLETE" && it.completionCount > 0 })
    }

    @Test
    fun testSoundEnabledSettingPreventsPlayback() = runTest {
        settingsRepo.updateSettings(AppSettings(soundEnabled = false))
        advanceUntilIdle()
        audioPlayer.playedFiles.clear()
        
        viewModel.playCurrentAudio()
        advanceUntilIdle()
        
        assertTrue(audioPlayer.playedFiles.isEmpty())
    }
}
