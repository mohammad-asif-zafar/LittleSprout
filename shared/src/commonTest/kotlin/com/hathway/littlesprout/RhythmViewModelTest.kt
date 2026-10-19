package com.hathway.littlesprout

import com.hathway.littlesprout.presentation.music.rhythm.RhythmMode
import com.hathway.littlesprout.presentation.music.rhythm.RhythmViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RhythmViewModelTest {

    @Test
    fun initialModeIsPlay() {
        val viewModel = RhythmViewModel()
        assertEquals(RhythmMode.PLAY, viewModel.currentMode.value)
        assertFalse(viewModel.isMuted.value)
        assertEquals(0, viewModel.currentTrackIndex.value)
        assertEquals("Dance Track 1", viewModel.currentTrackDisplayName)
    }

    @Test
    fun setModeUpdatesCurrentMode() {
        val viewModel = RhythmViewModel()
        viewModel.onScreenEntered()

        viewModel.setMode(RhythmMode.DANCE)
        assertEquals(RhythmMode.DANCE, viewModel.currentMode.value)

        viewModel.setMode(RhythmMode.COPY)
        assertEquals(RhythmMode.COPY, viewModel.currentMode.value)

        viewModel.setMode(RhythmMode.PLAY)
        assertEquals(RhythmMode.PLAY, viewModel.currentMode.value)
    }

    @Test
    fun nextDanceTrackAdvancesIndex() {
        val viewModel = RhythmViewModel()
        assertEquals(0, viewModel.currentTrackIndex.value)
        assertEquals("Dance Track 1", viewModel.currentTrackDisplayName)

        viewModel.nextDanceTrack()
        assertEquals(1, viewModel.currentTrackIndex.value)
        assertEquals("Dance Track 2", viewModel.currentTrackDisplayName)

        // Advance through all 12 tracks
        repeat(11) {
            viewModel.nextDanceTrack()
        }
        assertEquals(0, viewModel.currentTrackIndex.value)
        assertEquals("Dance Track 1", viewModel.currentTrackDisplayName)
    }

    @Test
    fun toggleMuteChangesMuteState() {
        val viewModel = RhythmViewModel()
        assertFalse(viewModel.isMuted.value)

        viewModel.toggleMute()
        assertTrue(viewModel.isMuted.value)

        viewModel.toggleMute()
        assertFalse(viewModel.isMuted.value)
    }

    @Test
    fun leavingScreenResetsSessionAndStopsAudio() {
        val viewModel = RhythmViewModel()
        viewModel.onScreenEntered()
        viewModel.setMode(RhythmMode.DANCE)

        assertEquals(RhythmMode.DANCE, viewModel.currentMode.value)

        viewModel.onScreenExited()

        assertEquals(RhythmMode.PLAY, viewModel.currentMode.value)
    }
}
