package com.hathway.littlesprout.presentation.parents

import com.hathway.littlesprout.fakes.FakeProgressRepository
import com.hathway.littlesprout.fakes.FakeSettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

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
    fun `all setting toggles update the repository correctly`() = runTest {
        val settingsRepo = FakeSettingsRepository()
        val progressRepo = FakeProgressRepository()
        val viewModel = SettingsViewModel(settingsRepo, progressRepo)

        // Initialize stateIn collectors using backgroundScope 
        // to prevent `SharingStarted.WhileSubscribed` timeouts
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.settings.collect { }
        }

        // Validate Default State
        assertTrue(viewModel.settings.value.soundEnabled)
        assertFalse(viewModel.settings.value.quietModeEnabled)

        // Act
        viewModel.updateSoundEnabled(false)
        viewModel.updateQuietModeEnabled(true)
        viewModel.updateMusicEnabled(false)
        viewModel.updateAutoPlayEnabled(false)
        viewModel.completeOnboarding()

        advanceUntilIdle() // Ensures updates suspend to completion

        // Assert Toggles Respected
        val newSettings = viewModel.settings.value
        assertFalse(newSettings.soundEnabled)
        assertTrue(newSettings.quietModeEnabled)
        assertFalse(newSettings.musicEnabled)
        assertFalse(newSettings.autoPlayEnabled)
        assertTrue(newSettings.isOnboardingCompleted)
    }

    @Test
    fun `resetProgress cascades correctly to progress repository`() = runTest {
        val settingsRepo = FakeSettingsRepository()
        val progressRepo = FakeProgressRepository()
        
        progressRepo.recordActivity("id1", "category", true)
        assertTrue(progressRepo.getProgressFlow().value.isNotEmpty())

        val viewModel = SettingsViewModel(settingsRepo, progressRepo)
        viewModel.resetProgress()
        
        advanceUntilIdle()
        assertTrue(progressRepo.getProgressFlow().value.isEmpty())
    }
}
