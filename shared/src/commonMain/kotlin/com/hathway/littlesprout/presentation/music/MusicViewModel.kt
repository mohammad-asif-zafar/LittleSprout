package com.hathway.littlesprout.presentation.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.model.AppSettings
import com.hathway.littlesprout.domain.model.MusicItem
import com.hathway.littlesprout.domain.model.MusicType
import com.hathway.littlesprout.domain.model.SongItem
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.domain.repository.SettingsRepository
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import littlesprout.shared.generated.resources.*

class MusicViewModel(
    private val progressRepository: ProgressRepository? = null,
    private val settingsRepository: SettingsRepository? = null
) : ViewModel() {
    private val audioPlayer = getAudioPlayer()

    private val _musicItems = MutableStateFlow(
        listOf(
            MusicItem("Sing Along", Res.drawable.img_music_sing_along, MusicType.SING_ALONG),
            MusicItem("Piano", Res.drawable.img_music_piano, MusicType.PIANO),
            MusicItem("Drums", Res.drawable.img_music_drums, MusicType.DRUMS),
            MusicItem("Rhythm", Res.drawable.img_music_rhythm, MusicType.RHYTHM)
        )
    )
    val musicItems = _musicItems.asStateFlow()

    private val _songs = MutableStateFlow(
        listOf(
            SongItem(
                title = "Clap Clap",
                lyrics = "Clap, clap, clap your hands,\nClap them high, clap them low!\nTap, tap, tap your toes,\nTap them fast, then nice and slow!",
                imageRes = Res.drawable.icon_clap_clap,
                backgroundColorLong = 0xFFE1F5FE,
                audioPath = "audio_clap_clap.mp3"
            ),
            SongItem(
                title = "Bunny Hop",
                lyrics = "Bunny hop, hop, hop,\nLittle bunny never stops!\nHop to the left,\nHop to the right,\nHop, hop, hop —\nWhat a funny sight!",
                imageRes = Res.drawable.icon_bunny_hop,
                backgroundColorLong = 0xFFE8F5E9,
                audioPath = "audio_bunny_hop.mp3"
            ),
            SongItem(
                title = "Hello Sun",
                lyrics = "Hello, sun! Hello, sky!\nWave your hands and say hi-hi!\nJump up high, touch your toes,\nWiggle, wiggle — off we go!",
                imageRes = Res.drawable.icon_hello_sun,
                backgroundColorLong = 0xFFFFF9C4,
                audioPath = "audio_hello_sun.mp3"
            ),
            SongItem(
                title = "Little Chick",
                lyrics = "Little chick goes peep, peep, peep!\nWakes up from a cozy sleep.\nWaddle left, waddle right,\nFlap your wings with all your might!",
                imageRes = Res.drawable.icon_little_chick,
                backgroundColorLong = 0xFFFCE4EC,
                audioPath = "audio_little_chick.mp3"
            ),
            SongItem(
                title = "Zoom Zoom Car",
                lyrics = "Zoom, zoom, little car,\nRound the room and not too far!\nBeep-beep here,\nBeep-beep there,\nZoom around with happy care!",
                imageRes = Res.drawable.icon_zoom_zoom_car,
                backgroundColorLong = 0xFFFCE4EC,
                audioPath = "audio_zoom_zoom.mp3"
            )
        )
    )

    val songs = _songs.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _playbackProgress = MutableStateFlow(0f)
    val playbackProgress = _playbackProgress.asStateFlow()

    private val _currentTime = MutableStateFlow("00:00")
    val currentTime = _currentTime.asStateFlow()

    private val _totalDuration = MutableStateFlow("00:00")
    val totalDuration = _totalDuration.asStateFlow()

    private var currentSong: SongItem? = null

    private val appSettings: StateFlow<AppSettings> = settingsRepository?.settings
        ?.stateIn(viewModelScope, SharingStarted.Eagerly, AppSettings())
        ?: MutableStateFlow(AppSettings())

    init {
        audioPlayer.onPlaybackComplete {
            _isPlaying.value = false
            _playbackProgress.value = 0f
            _currentTime.value = "00:00"
            currentSong?.let { recordProgress(it.title, true) }
        }
        startProgressTracker()
    }

    private fun startProgressTracker() {
        viewModelScope.launch {
            while (true) {
                if (_isPlaying.value) {
                    val current = audioPlayer.getCurrentPosition()
                    val total = audioPlayer.getDuration()

                    if (total > 0) {
                        _playbackProgress.value = current.toFloat() / total.toFloat()
                        _currentTime.value = formatTime(current)
                        _totalDuration.value = formatTime(total)
                    }
                }
                delay(500)
            }
        }
    }

    private fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    }

    fun playSong(song: SongItem) {
        if (!appSettings.value.musicEnabled) return
        
        currentSong = song
        song.audioPath.let {
            audioPlayer.play(it)
            _isPlaying.value = true
            recordProgress(song.title, false)
        }
    }

    fun togglePlayPause() {
        if (_isPlaying.value) {
            audioPlayer.pause()
            _isPlaying.value = false
        } else {
            if (!appSettings.value.musicEnabled) return
            audioPlayer.resume()
            _isPlaying.value = true
        }
    }

    fun seekTo(progress: Float) {
        val total = audioPlayer.getDuration()
        if (total > 0) {
            val newPos = (progress * total).toLong()
            audioPlayer.seekTo(newPos)
            _playbackProgress.value = progress
        }
    }

    fun stopMusic() {
        audioPlayer.stop()
        _isPlaying.value = false
        _playbackProgress.value = 0f
        _currentTime.value = "00:00"
    }

    private fun recordProgress(activityId: String, completed: Boolean) {
        viewModelScope.launch {
            progressRepository?.recordActivity(activityId, CategoryConstants.SONGS, completed)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.stop()
    }
}
