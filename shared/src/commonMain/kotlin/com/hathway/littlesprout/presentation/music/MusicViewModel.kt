package com.hathway.littlesprout.presentation.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.model.MusicItem
import com.hathway.littlesprout.domain.model.MusicType
import com.hathway.littlesprout.domain.model.SongItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
            SongItem(
                title = "Clap Clap",
                lyrics = "\uD83C\uDFB5  Clap Clap Song \n\n" +
                        "Clap, clap, clap your hands, \n" +
                        "Clap them high, clap them low! \n" +
                        "Tap, tap, tap your toes, \n" +
                        "Tap them fast, then nice and slow! \n\n" +
                        "Clap, clap — hooray! \n" +
                        "Tap, tap — play! \n" +
                        "Clap and tap, clap and tap, \n" +
                        "Let’s do it again! ",
                imageRes = Res.drawable.icon_clap_clap,
                backgroundColorLong = 0xFFE1F5FE,
                audioPath = "audio_clap_clap.mp3"
            ),
            SongItem(
                title = "Bunny Hop",
                lyrics = "\uD83D\uDC30  Bunny Hop \n\n" +
                        "Bunny hop, hop, hop, \n" +
                        "Little bunny never stops! \n" +
                        "Hop to the left, \n" +
                        "Hop to the right, \n" +
                        "Hop, hop, hop — \n" +
                        "What a funny sight! ",
                imageRes = Res.drawable.icon_bunny_hop,
                backgroundColorLong = 0xFFE8F5E9,
                audioPath = "audio_bunny_hop.mp3"
            ),
            SongItem(
                title = "Hello Sun",
                lyrics = "☀\uFE0F  Hello, Sun! \n\n" +
                        "Hello, sun! Hello, sky! \n" +
                        "Wave your hands and say hi-hi! \n" +
                        "Jump up high, touch your toes, \n" +
                        "Wiggle, wiggle — off we go! \n\n" +
                        "Hi-hi! Bye-bye! \n" +
                        "Wave up high! \n" +
                        "Hello, sun, hello, sky, \n" +
                        "See you soon — bye-bye! ",
                imageRes = Res.drawable.icon_hello_sun,
                backgroundColorLong = 0xFFFFF9C4,
                audioPath = "audio_hello_sun.mp3"
            ),
            SongItem(
                title = "Little Chick",
                lyrics = "\uD83D\uDC25  Little Chick \n\n" +
                        "Little chick goes peep, peep, peep! \n" +
                        "Wakes up from a cozy sleep. \n" +
                        "Waddle left, waddle right, \n" +
                        "Flap your wings with all your might! \n\n" +
                        "Peep-peep-peep! \n" +
                        "Tweet-tweet-tweet! \n" +
                        "Little chick has dancing feet! ",
                imageRes = Res.drawable.icon_little_chick,
                backgroundColorLong = 0xFFFCE4EC,
                audioPath = "audio_little_chick.mp3"
            ),
            SongItem(
                title = "Zoom Zoom Car",
                lyrics = "\uD83D\uDE97  Zoom Zoom Car \n\n" +
                        "Zoom, zoom, little car, \n" +
                        "Round the room and not too far! \n" +
                        "Beep-beep here, \n" +
                        "Beep-beep there, \n" +
                        "Zoom around with happy care! \n\n" +
                        "Zoom, zoom! Beep, beep! \n" +
                        "Round and round we go! \n" +
                        "Zoom, zoom, little car, \n" +
                        "Fast, then nice and slow! ",
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

    init {
        audioPlayer.onPlaybackComplete {
            _isPlaying.value = false
            _playbackProgress.value = 0f
            _currentTime.value = "00:00"
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
        println("MusicViewModel: Requested to play song: ${song.title} with res: ${song.audioPath}")
        song.audioPath?.let {
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

    override fun onCleared() {
        super.onCleared()
        audioPlayer.stop()
    }
}
