package com.hathway.littlesprout.presentation.common_components

import com.hathway.littlesprout.domain.model.AppSettings
import com.hathway.littlesprout.fakes.FakeAudioPlayer
import com.hathway.littlesprout.fakes.FakeProgressRepository
import com.hathway.littlesprout.fakes.FakeSettingsRepository
import com.hathway.littlesprout.presentation.util.CategoryConstants
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CommonViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeProgressRepository: FakeProgressRepository
    private lateinit var fakeSettingsRepository: FakeSettingsRepository
    private lateinit var fakeAudioPlayer: FakeAudioPlayer

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeProgressRepository = FakeProgressRepository()
        fakeSettingsRepository = FakeSettingsRepository()
        fakeAudioPlayer = FakeAudioPlayer()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private suspend fun createViewModel(appSettings: AppSettings = AppSettings()): CommonViewModel {
        fakeSettingsRepository.updateSettings(appSettings)
        val viewModel = CommonViewModel(
            progressRepository = fakeProgressRepository,
            settingsRepository = fakeSettingsRepository,
            injectedAudioPlayer = fakeAudioPlayer
        )
        // allow settings to propagate
        testDispatcher.scheduler.advanceUntilIdle()
        return viewModel
    }

    @Test
    fun `initial state is correct`() = runTest(testDispatcher) {
        val viewModel = createViewModel()

        assertEquals(0, viewModel.currentBirdIndex.value)
        assertEquals(0, viewModel.currentFruitIndex.value)
        assertEquals(0, viewModel.currentVehicleIndex.value)
        assertFalse(viewModel.isPlaying.value)

        assertTrue(viewModel.birdList.value.isNotEmpty())
        assertTrue(viewModel.fruitsList.value.isNotEmpty())
        assertTrue(viewModel.vehicleList.value.isNotEmpty())
    }

    @Test
    fun `playInitialAudio plays audio when autoPlayEnabled is true`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(autoPlayEnabled = true, soundEnabled = true, quietModeEnabled = false))
        
        viewModel.playInitialAudio(CategoryConstants.BIRDS)
        
        assertTrue(viewModel.isPlaying.value)
        assertTrue(fakeAudioPlayer.isPlayingFlag)
        assertEquals(viewModel.birdList.value[0].audio, fakeAudioPlayer.playedFiles.lastOrNull())
    }

    @Test
    fun `playInitialAudio does not play audio when autoPlayEnabled is false`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(autoPlayEnabled = false, soundEnabled = true, quietModeEnabled = false))
        
        viewModel.playInitialAudio(CategoryConstants.BIRDS)
        
        assertFalse(viewModel.isPlaying.value)
        assertFalse(fakeAudioPlayer.isPlayingFlag)
        assertTrue(fakeAudioPlayer.playedFiles.isEmpty())
    }

    @Test
    fun `playInitialAudio respects quietModeEnabled and soundEnabled settings`() = runTest(testDispatcher) {
        // Test soundEnabled = false
        var viewModel = createViewModel(AppSettings(autoPlayEnabled = true, soundEnabled = false, quietModeEnabled = false))
        viewModel.playInitialAudio(CategoryConstants.BIRDS)
        assertFalse(viewModel.isPlaying.value)

        // Test quietModeEnabled = true
        fakeAudioPlayer.playedFiles.clear()
        viewModel = createViewModel(AppSettings(autoPlayEnabled = true, soundEnabled = true, quietModeEnabled = true))
        viewModel.playInitialAudio(CategoryConstants.BIRDS)
        assertFalse(viewModel.isPlaying.value)
    }

    @Test
    fun `nextItem advances index and plays audio`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(soundEnabled = true, quietModeEnabled = false))
        
        assertEquals(0, viewModel.currentFruitIndex.value)
        
        viewModel.nextItem(CategoryConstants.FRUITS)
        
        assertEquals(1, viewModel.currentFruitIndex.value)
        assertTrue(viewModel.isPlaying.value)
        assertEquals(viewModel.fruitsList.value[1].audio, fakeAudioPlayer.playedFiles.lastOrNull())
    }

    @Test
    fun `nextItem records progress on complete`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(soundEnabled = true, quietModeEnabled = false))
        
        // Fast forward to the end of the fruits list
        val maxIndex = viewModel.fruitsList.value.size - 1
        repeat(maxIndex) {
            viewModel.nextItem(CategoryConstants.FRUITS)
        }
        
        assertEquals(maxIndex, viewModel.currentFruitIndex.value)
        
        // Next should trigger completion
        viewModel.nextItem(CategoryConstants.FRUITS)
        testDispatcher.scheduler.advanceUntilIdle() // let coroutine finish
        
        assertEquals(maxIndex, viewModel.currentFruitIndex.value) // Should not exceed max bounds
        val progress = fakeProgressRepository.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "${CategoryConstants.FRUITS.uppercase()}_COMPLETE" && it.completionCount > 0 })
    }

    @Test
    fun `previousItem goes back and plays audio`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(soundEnabled = true, quietModeEnabled = false))
        
        viewModel.nextItem(CategoryConstants.VEHICLE)
        assertEquals(1, viewModel.currentVehicleIndex.value)
        
        fakeAudioPlayer.playedFiles.clear()
        
        viewModel.previousItem(CategoryConstants.VEHICLE)
        
        assertEquals(0, viewModel.currentVehicleIndex.value)
        assertTrue(viewModel.isPlaying.value)
        assertEquals(viewModel.vehicleList.value[0].audio, fakeAudioPlayer.playedFiles.lastOrNull())
    }

    @Test
    fun `previousItem respects lower bounds`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(soundEnabled = true, quietModeEnabled = false))
        
        assertEquals(0, viewModel.currentBirdIndex.value)
        
        viewModel.previousItem(CategoryConstants.BIRDS)
        
        assertEquals(0, viewModel.currentBirdIndex.value) // Cannot go below 0
    }

    @Test
    fun `toggleAudioPlayback plays specific audio if settings allow`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(soundEnabled = true, quietModeEnabled = false))
        
        val customAudioFile = "test_audio.mp3"
        viewModel.toggleAudioPlayback(customAudioFile)
        
        assertTrue(viewModel.isPlaying.value)
        assertEquals(customAudioFile, fakeAudioPlayer.playedFiles.lastOrNull())
    }

    @Test
    fun `audio playback complete updates state`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(autoPlayEnabled = true, soundEnabled = true, quietModeEnabled = false))
        
        viewModel.playInitialAudio(CategoryConstants.BIRDS)
        assertTrue(viewModel.isPlaying.value)
        
        fakeAudioPlayer.triggerCompletion()
        
        assertFalse(viewModel.isPlaying.value)
    }

    @Test
    fun `stopAudio updates state and stops player`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(autoPlayEnabled = true, soundEnabled = true, quietModeEnabled = false))
        
        viewModel.playInitialAudio(CategoryConstants.BIRDS)
        assertTrue(viewModel.isPlaying.value)
        
        viewModel.stopAudio()
        
        assertFalse(viewModel.isPlaying.value)
        assertTrue(fakeAudioPlayer.isStopped)
    }
}
