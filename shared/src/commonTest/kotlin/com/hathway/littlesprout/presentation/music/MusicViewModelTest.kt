package com.hathway.littlesprout.presentation.music

import com.hathway.littlesprout.domain.model.AppSettings
import com.hathway.littlesprout.fakes.FakeAudioPlayer
import com.hathway.littlesprout.fakes.FakeProgressRepository
import com.hathway.littlesprout.fakes.FakeSettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
class MusicViewModelTest {

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

    private suspend fun createViewModel(appSettings: AppSettings = AppSettings()): MusicViewModel {
        fakeSettingsRepository.updateSettings(appSettings)
        val viewModel = MusicViewModel(
            progressRepository = fakeProgressRepository,
            settingsRepository = fakeSettingsRepository,
            injectedAudioPlayer = fakeAudioPlayer
        )
        // allow settings and state initialization to propagate
        testDispatcher.scheduler.advanceUntilIdle()
        return viewModel
    }

    @Test
    fun `initial state is correct`() = runTest(testDispatcher) {
        val viewModel = createViewModel()

        assertTrue(viewModel.musicItems.value.isNotEmpty())
        assertTrue(viewModel.songs.value.isNotEmpty())
        assertFalse(viewModel.isPlaying.value)
        assertEquals(0f, viewModel.playbackProgress.value)
        assertEquals("00:00", viewModel.currentTime.value)
    }

    @Test
    fun `playSong starts audio and updates state if musicEnabled is true`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        val firstSong = viewModel.songs.value.first()

        viewModel.playSong(firstSong)

        assertTrue(viewModel.isPlaying.value)
        assertTrue(fakeAudioPlayer.isPlayingFlag)
        assertEquals(firstSong.audioPath, fakeAudioPlayer.playedFiles.lastOrNull())
    }

    @Test
    fun `playSong does not start audio if musicEnabled is false`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = false))
        val firstSong = viewModel.songs.value.first()

        viewModel.playSong(firstSong)

        assertFalse(viewModel.isPlaying.value)
        assertFalse(fakeAudioPlayer.isPlayingFlag)
        assertTrue(fakeAudioPlayer.playedFiles.isEmpty())
    }

    @Test
    fun `togglePlayPause pauses playing music`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        val firstSong = viewModel.songs.value.first()

        viewModel.playSong(firstSong)
        assertTrue(viewModel.isPlaying.value)

        viewModel.togglePlayPause()

        assertFalse(viewModel.isPlaying.value)
        assertTrue(fakeAudioPlayer.isPaused)
        assertFalse(fakeAudioPlayer.isPlayingFlag)
    }

    @Test
    fun `togglePlayPause resumes paused music`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        val firstSong = viewModel.songs.value.first()

        viewModel.playSong(firstSong)
        viewModel.togglePlayPause() // Paused
        assertFalse(viewModel.isPlaying.value)
        
        viewModel.togglePlayPause() // Resumed
        assertTrue(viewModel.isPlaying.value)
        assertFalse(fakeAudioPlayer.isPaused)
        assertTrue(fakeAudioPlayer.isPlayingFlag)
    }

    @Test
    fun `stopMusic updates state and stops player`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        val firstSong = viewModel.songs.value.first()

        viewModel.playSong(firstSong)
        viewModel.stopMusic()

        assertFalse(viewModel.isPlaying.value)
        assertEquals(0f, viewModel.playbackProgress.value)
        assertEquals("00:00", viewModel.currentTime.value)
        assertTrue(fakeAudioPlayer.isStopped)
    }

    @Test
    fun `rewindSong seeks backward if current position is greater than 3000ms`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        val firstSong = viewModel.songs.value.first()

        viewModel.playSong(firstSong)
        
        fakeAudioPlayer.customDuration = 10000L
        fakeAudioPlayer.customPosition = 6000L
        
        viewModel.rewindSong()
        
        // Should seek back 5000ms from 6000ms
        assertEquals(1000L, fakeAudioPlayer.lastSeekPosition)
    }

    @Test
    fun `rewindSong restarts song if current position is less than or equal to 3000ms`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        val firstSong = viewModel.songs.value.first()

        viewModel.playSong(firstSong)
        
        fakeAudioPlayer.customDuration = 10000L
        fakeAudioPlayer.customPosition = 2000L
        fakeAudioPlayer.playedFiles.clear()
        
        viewModel.rewindSong()
        
        // Should replay the song
        assertEquals(firstSong.audioPath, fakeAudioPlayer.playedFiles.lastOrNull())
    }

    @Test
    fun `skipToNextSong plays the next song in the list`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        val firstSong = viewModel.songs.value[0]
        val secondSong = viewModel.songs.value[1]

        viewModel.playSong(firstSong)
        viewModel.skipToNextSong()
        
        assertEquals(secondSong.audioPath, fakeAudioPlayer.playedFiles.lastOrNull())
    }

    @Test
    fun `skipToNextSong wraps to first song if at end of list`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        val lastSong = viewModel.songs.value.last()
        val firstSong = viewModel.songs.value.first()

        viewModel.playSong(lastSong)
        viewModel.skipToNextSong()
        
        assertEquals(firstSong.audioPath, fakeAudioPlayer.playedFiles.lastOrNull())
    }

    @Test
    fun `onPlaybackComplete resets playback state`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        val firstSong = viewModel.songs.value.first()

        viewModel.playSong(firstSong)
        assertTrue(viewModel.isPlaying.value)
        
        fakeAudioPlayer.triggerCompletion()
        
        assertFalse(viewModel.isPlaying.value)
        assertEquals(0f, viewModel.playbackProgress.value)
        assertEquals("00:00", viewModel.currentTime.value)
    }

    @Test
    fun `seekTo calculates correct position and calls seekTo on player`() = runTest(testDispatcher) {
        val viewModel = createViewModel(AppSettings(musicEnabled = true))
        
        fakeAudioPlayer.customDuration = 10000L
        viewModel.seekTo(0.5f)
        
        assertEquals(5000L, fakeAudioPlayer.lastSeekPosition)
        assertEquals(0.5f, viewModel.playbackProgress.value)
    }
}
