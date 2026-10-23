package com.hathway.littlesprout

import com.hathway.littlesprout.presentation.music.AudioPlayer
import com.hathway.littlesprout.presentation.music.drums.DrumSound
import com.hathway.littlesprout.presentation.music.rhythm.RhythmMode
import com.hathway.littlesprout.presentation.music.rhythm.RhythmViewModel
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
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import com.hathway.littlesprout.fakes.FakeAudioPlayer

@OptIn(ExperimentalCoroutinesApi::class)
class RhythmViewModelTest {

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
    fun `initial state is correct`() {
        val viewModel = RhythmViewModel()
        assertEquals(RhythmMode.PLAY, viewModel.currentMode.value)
        assertFalse(viewModel.isMuted.value)
        assertEquals(0, viewModel.currentTrackIndex.value)
        assertEquals("Dance Track 1", viewModel.currentTrackDisplayName)
        assertFalse(viewModel.isShowingDemo.value)
    }

    @Test
    fun `setMode updates currentMode and stops audio on exit dance mode`() {
        val fakeAudioPlayer = FakeAudioPlayer()
        val viewModel = RhythmViewModel(injectedAudioPlayer = fakeAudioPlayer)
        viewModel.onScreenEntered()

        viewModel.setMode(RhythmMode.DANCE)
        assertEquals(RhythmMode.DANCE, viewModel.currentMode.value)
        assertTrue(fakeAudioPlayer.playedFiles.contains(viewModel.danceTracks[0]))

        fakeAudioPlayer.isStopped = false
        viewModel.setMode(RhythmMode.PLAY)
        assertEquals(RhythmMode.PLAY, viewModel.currentMode.value)
        assertTrue(fakeAudioPlayer.isStopped)
    }

    @Test
    fun `nextDanceTrack advances index and auto plays in dance mode`() {
        val fakeAudioPlayer = FakeAudioPlayer()
        val viewModel = RhythmViewModel(injectedAudioPlayer = fakeAudioPlayer)
        viewModel.onScreenEntered()
        viewModel.setMode(RhythmMode.DANCE)

        assertEquals(0, viewModel.currentTrackIndex.value)
        fakeAudioPlayer.playedFiles.clear()

        viewModel.nextDanceTrack()
        assertEquals(1, viewModel.currentTrackIndex.value)
        assertEquals("Dance Track 2", viewModel.currentTrackDisplayName)
        assertTrue(fakeAudioPlayer.playedFiles.contains(viewModel.danceTracks[1]))

        // Advance through all tracks
        repeat(11) {
            viewModel.nextDanceTrack()
        }
        assertEquals(0, viewModel.currentTrackIndex.value)
    }

    @Test
    fun `toggleMute changes mute state and controls audio in dance mode`() {
        val fakeAudioPlayer = FakeAudioPlayer()
        val viewModel = RhythmViewModel(injectedAudioPlayer = fakeAudioPlayer)
        viewModel.onScreenEntered()
        viewModel.setMode(RhythmMode.DANCE)

        assertFalse(viewModel.isMuted.value)
        fakeAudioPlayer.isStopped = false

        viewModel.toggleMute() // Muting
        assertTrue(viewModel.isMuted.value)
        assertTrue(fakeAudioPlayer.isStopped)

        fakeAudioPlayer.playedFiles.clear()
        viewModel.toggleMute() // Unmuting
        assertFalse(viewModel.isMuted.value)
        assertTrue(fakeAudioPlayer.playedFiles.contains(viewModel.danceTracks[viewModel.currentTrackIndex.value]))
    }

    @Test
    fun `leaving screen resets session and stops audio`() {
        val fakeAudioPlayer = FakeAudioPlayer()
        val viewModel = RhythmViewModel(injectedAudioPlayer = fakeAudioPlayer)
        viewModel.onScreenEntered()
        viewModel.setMode(RhythmMode.DANCE)

        fakeAudioPlayer.isStopped = false
        viewModel.onScreenExited()

        assertEquals(RhythmMode.PLAY, viewModel.currentMode.value)
        assertTrue(fakeAudioPlayer.isStopped)
    }

    // --- EDGE CASE TESTS ---

    @Test
    fun `resilience - exceptions thrown by AudioPlayer are caught gracefully`() {
        val failingAudioPlayer = FakeAudioPlayer(throwsException = true)
        val viewModel = RhythmViewModel(injectedAudioPlayer = failingAudioPlayer)

        viewModel.onScreenEntered()
        viewModel.setMode(RhythmMode.DANCE)
        viewModel.playDrum(DrumSound.KICK, "pad1")
        viewModel.pauseAudio()
        viewModel.resumeAudioIfNeeded()
        viewModel.toggleMute()
        viewModel.onScreenExited()

        // If it doesn't throw and crash the test suite, resilience is confirmed.
    }

    @Test
    fun `copy mode - hitting correct sequence advances step`() {
        val viewModel = RhythmViewModel()
        viewModel.setMode(RhythmMode.COPY)
        assertEquals(0, viewModel.copyStep.value)

        // Hitting first correct drum
        viewModel.playDrum(DrumSound.KICK, "pad1")
        assertEquals(1, viewModel.copyStep.value)

        // Hitting second correct drum
        viewModel.playDrum(DrumSound.SNARE, "pad2")
        assertEquals(2, viewModel.copyStep.value)
    }

    @Test
    fun `copy mode - hitting incorrect sequence does not advance step`() {
        val viewModel = RhythmViewModel()
        viewModel.setMode(RhythmMode.COPY)
        assertEquals(0, viewModel.copyStep.value)

        // Hitting incorrect drum (Expected KICK, hitting OPEN_HAT)
        viewModel.playDrum(DrumSound.OPEN_HAT, "pad1")
        assertEquals(0, viewModel.copyStep.value)
    }

    @Test
    fun `copy mode - completing sequence resets step and starts demo`() = runTest {
        val viewModel = RhythmViewModel()
        viewModel.onScreenEntered()
        viewModel.setMode(RhythmMode.COPY)
        advanceTimeBy(5000) // fast forward past initial demo

        // Complete the sequence: KICK, SNARE, KICK, CLOSED_HAT
        viewModel.playDrum(DrumSound.KICK, "pad1")
        viewModel.playDrum(DrumSound.SNARE, "pad2")
        viewModel.playDrum(DrumSound.KICK, "pad3")
        viewModel.playDrum(DrumSound.CLOSED_HAT, "pad4")

        // Should reset to 0 and trigger demo flag
        assertEquals(0, viewModel.copyStep.value)
        assertTrue(viewModel.isShowingDemo.value)
    }

    @Test
    fun `copy mode - playing drum while demo is showing is ignored`() = runTest {
        val viewModel = RhythmViewModel()
        viewModel.onScreenEntered()
        viewModel.setMode(RhythmMode.COPY)
        // Note: Demo takes a few seconds to run, so isShowingDemo is true right now.
        assertTrue(viewModel.isShowingDemo.value)

        // Hit correct drum, but since demo is playing it should be ignored.
        viewModel.playDrum(DrumSound.KICK, "pad1")
        assertEquals(0, viewModel.copyStep.value)
    }

    @Test
    fun `lifecycle constraints - resumeAudioIfNeeded respects state`() {
        val fakeAudioPlayer = FakeAudioPlayer()
        val viewModel = RhythmViewModel(injectedAudioPlayer = fakeAudioPlayer)

        // Attempt resume while screen is inactive
        viewModel.onScreenExited()
        viewModel.setMode(RhythmMode.DANCE)
        fakeAudioPlayer.isPlayingFlag = false
        fakeAudioPlayer.playedFiles.clear()
        viewModel.resumeAudioIfNeeded()
        assertFalse(fakeAudioPlayer.isPlayingFlag)
        assertTrue(fakeAudioPlayer.playedFiles.isEmpty())

        // Attempt resume while muted
        viewModel.onScreenEntered()
        viewModel.toggleMute() // Mute
        viewModel.resumeAudioIfNeeded()
        assertFalse(fakeAudioPlayer.isPlayingFlag)
        assertTrue(fakeAudioPlayer.playedFiles.isEmpty())
    }

    @Test
    fun `auto-advance constraints - track does not auto advance if mode changed`() = runTest {
        val fakeAudioPlayer = FakeAudioPlayer()
        val viewModel = RhythmViewModel(injectedAudioPlayer = fakeAudioPlayer)
        viewModel.onScreenEntered()

        viewModel.setMode(RhythmMode.DANCE)
        assertEquals(0, viewModel.currentTrackIndex.value)

        // Change mode to PLAY
        viewModel.setMode(RhythmMode.PLAY)

        // Simulate track completion from the previous DANCE mode session
        fakeAudioPlayer.triggerCompletion()

        // Wait for coroutine inside onPlaybackComplete to run
        advanceTimeBy(100)

        // Verify index did not advance because mode is now PLAY
        assertEquals(0, viewModel.currentTrackIndex.value)
    }

    @Test
    fun `auto-advance constraints - track does not auto advance if screen exited`() = runTest {
        val fakeAudioPlayer = FakeAudioPlayer()
        val viewModel = RhythmViewModel(injectedAudioPlayer = fakeAudioPlayer)
        viewModel.onScreenEntered()

        viewModel.setMode(RhythmMode.DANCE)
        assertEquals(0, viewModel.currentTrackIndex.value)

        // Exit screen
        viewModel.onScreenExited()

        // Simulate track completion from the background
        fakeAudioPlayer.triggerCompletion()

        // Wait for coroutine inside onPlaybackComplete to run
        advanceTimeBy(100)

        // Verify index did not advance because screen is not active
        assertEquals(0, viewModel.currentTrackIndex.value)
    }
}
