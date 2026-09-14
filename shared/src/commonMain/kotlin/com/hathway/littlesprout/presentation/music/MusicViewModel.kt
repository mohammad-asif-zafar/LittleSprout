package com.hathway.littlesprout.presentation.music

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.MusicItem
import com.hathway.littlesprout.domain.model.MusicType
import com.hathway.littlesprout.domain.model.SongItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class MusicViewModel : ViewModel() {
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
            SongItem("Old MacDonald", Res.drawable.img_music_old_macdonald, 0xFFE1F5FE, "a_apple.mp3"),
            SongItem("Twinkle Twinkle\nLittle Star", Res.drawable.img_music_twinkle, 0xFFE8F5E9, "TunePocket-Old-Mcdonald-Had-A-Farm-Preview.mp3"),
            SongItem("The Wheels\non the Bus", Res.drawable.img_music_bus, 0xFFFFF9C4, "wheels_on_bus.mp3"),
            SongItem("If You're\nHappy", Res.drawable.img_music_happy, 0xFFFCE4EC, "TunePocket-Old-Mcdonald-Had-A-Farm-Preview.mp3")
        )
    )
    val songs = _songs.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    fun playSong(song: SongItem) {
        println("MusicViewModel: Requested to play song: ${song.title} with res: ${song.audioRes}")
        song.audioRes?.let { 
            audioPlayer.play(it) 
            _isPlaying.value = true
        }
    }

    fun togglePlayPause() {
        if (_isPlaying.value) {
            audioPlayer.pause()
            _isPlaying.value = false
        } else {
            audioPlayer.resume()
            _isPlaying.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.stop()
    }
}