package com.hathway.littlesprout.presentation.music.paino

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
class PianoViewModelTest {
    private lateinit var viewModel: PianoViewModel
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
        viewModel = PianoViewModel(progressRepo, settingsRepo, audioPlayer)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialState() {
        assertFalse(viewModel.isMuted.value)
        assertNull(viewModel.activeNoteIndex.value)
        assertFalse(viewModel.isSongMode.value)
        assertEquals(0, viewModel.songStep.value)
        assertTrue(viewModel.notes.isNotEmpty())
    }

    @Test
    fun testPlayNotePlaysAudioAndRecordsProgress() = runTest {
        viewModel.playNote(0)
        advanceUntilIdle()

        val expectedNote = viewModel.notes[0]
        assertTrue(audioPlayer.playedFiles.contains(expectedNote.soundRes))
        assertEquals(0, viewModel.activeNoteIndex.value)

        val progress = progressRepo.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "PIANO_NOTE_${expectedNote.note}" && it.playCount > 0 })
    }

    @Test
    fun testPlayNoteRespectsMute() = runTest {
        viewModel.toggleMute()
        advanceUntilIdle()
        assertTrue(viewModel.isMuted.value)

        viewModel.playNote(0)
        advanceUntilIdle()

        val expectedNote = viewModel.notes[0]
        assertFalse(audioPlayer.playedFiles.contains(expectedNote.soundRes))
        assertEquals(0, viewModel.activeNoteIndex.value)
    }

    @Test
    fun testReleaseNoteClearsActiveNote() {
        viewModel.playNote(0)
        assertEquals(0, viewModel.activeNoteIndex.value)

        viewModel.releaseNote(0)
        assertNull(viewModel.activeNoteIndex.value)
    }

    @Test
    fun testSongModeSequenceProgress() = runTest {
        viewModel.toggleSongMode()
        assertTrue(viewModel.isSongMode.value)
        assertEquals(0, viewModel.songStep.value)

        val targetSequence = viewModel.songSequence

        // Play correct first note
        viewModel.playNote(targetSequence[0])
        assertEquals(1, viewModel.songStep.value)

        // Play incorrect note (doesn't advance)
        val incorrectNote = (targetSequence[1] + 1) % viewModel.notes.size
        viewModel.playNote(incorrectNote)
        assertEquals(1, viewModel.songStep.value)
        
        // Play correct second note
        viewModel.playNote(targetSequence[1])
        assertEquals(2, viewModel.songStep.value)
    }

    @Test
    fun testSongModeCompletion() = runTest {
        viewModel.toggleSongMode()
        
        val sequence = viewModel.songSequence
        for (noteIndex in sequence) {
            viewModel.playNote(noteIndex)
        }

        advanceUntilIdle()
        assertEquals(0, viewModel.songStep.value)

        val progress = progressRepo.getProgressFlow().value
        assertTrue(progress.any { it.activityId == "PIANO_SONG_COMPLETE" && it.completionCount > 0 })
    }

    @Test
    fun testToggleSongModeResetsProgress() {
        viewModel.toggleSongMode()
        viewModel.playNote(viewModel.songSequence[0])
        assertEquals(1, viewModel.songStep.value)

        viewModel.toggleSongMode() // Turn off
        assertFalse(viewModel.isSongMode.value)
        assertEquals(0, viewModel.songStep.value)
    }
}
