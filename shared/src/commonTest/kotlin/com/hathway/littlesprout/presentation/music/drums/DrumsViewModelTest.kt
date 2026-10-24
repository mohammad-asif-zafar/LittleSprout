package com.hathway.littlesprout.presentation.music.drums

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
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DrumsViewModelTest {
    private lateinit var viewModel: DrumsViewModel
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
        viewModel = DrumsViewModel(progressRepo, settingsRepo, audioPlayer)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialState() {
        assertFalse(viewModel.isMuted.value)
        assertNull(viewModel.activePadId.value)
        assertFalse(viewModel.isRhythmMode.value)
        assertEquals(0, viewModel.rhythmStep.value)
    }

    @Test
    fun testPlayDrumPlaysAudioAndRecordsProgress() = runTest {
        viewModel.playDrum(DrumSound.KICK, "kick_pad")
        advanceUntilIdle()

        assertTrue(audioPlayer.playedFiles.contains(DrumSound.KICK.fileName))
        assertEquals("kick_pad", viewModel.activePadId.value)

        val progress = progressRepo.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "DRUM_${DrumSound.KICK.name}" && it.playCount > 0 })
    }

    @Test
    fun testPlayDrumRespectsMute() = runTest {
        viewModel.toggleMute()
        advanceUntilIdle()
        assertTrue(viewModel.isMuted.value)

        viewModel.playDrum(DrumSound.KICK, "kick_pad")
        advanceUntilIdle()

        assertFalse(audioPlayer.playedFiles.contains(DrumSound.KICK.fileName))
        assertEquals("kick_pad", viewModel.activePadId.value)
    }

    @Test
    fun testReleaseDrumClearsActivePad() {
        viewModel.playDrum(DrumSound.KICK, "kick_pad")
        assertEquals("kick_pad", viewModel.activePadId.value)

        viewModel.releaseDrum("kick_pad")
        assertNull(viewModel.activePadId.value)
    }

    @Test
    fun testRhythmModeSequenceProgress() = runTest {
        viewModel.toggleRhythmMode()
        assertTrue(viewModel.isRhythmMode.value)
        assertEquals(0, viewModel.rhythmStep.value)

        val firstTarget = viewModel.rhythmSequence[0]
        viewModel.playDrum(firstTarget, "pad1")
        assertEquals(1, viewModel.rhythmStep.value)

        val secondTarget = viewModel.rhythmSequence[1]
        viewModel.playDrum(secondTarget, "pad2")
        assertEquals(2, viewModel.rhythmStep.value)
    }

    @Test
    fun testRhythmModeCompletion() = runTest {
        viewModel.toggleRhythmMode()
        
        for (i in viewModel.rhythmSequence.indices) {
            viewModel.playDrum(viewModel.rhythmSequence[i], "pad_$i")
        }

        advanceUntilIdle()
        assertEquals(0, viewModel.rhythmStep.value)

        val progress = progressRepo.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "DRUMS_RHYTHM_COMPLETE" && it.completionCount > 0 })
    }

    @Test
    fun testToggleRhythmModeResetsStep() {
        viewModel.toggleRhythmMode()
        viewModel.playDrum(viewModel.rhythmSequence[0], "pad")
        assertEquals(1, viewModel.rhythmStep.value)

        viewModel.toggleRhythmMode() // Turn off
        assertFalse(viewModel.isRhythmMode.value)
        assertEquals(0, viewModel.rhythmStep.value)
    }
}
