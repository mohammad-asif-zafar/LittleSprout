package com.hathway.littlesprout.presentation.music

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.littlesprout.domain.model.SongItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_clap_clap

@Composable
fun MusicPlayerScreen(
    viewModel: MusicViewModel,
    song: SongItem,
    onBackClick: () -> Unit
) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.playbackProgress.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val totalTime by viewModel.totalDuration.collectAsState()

    LaunchedEffect(song) {
        viewModel.playSong(song)
    }

    MusicPlayerComponent(
        song = song,
        onBackClick = {
            viewModel.stopMusic()
            onBackClick()
        },
        onPlayPauseClick = { viewModel.togglePlayPause() },
        onRewindClick = { /* Handle rewind */ },
        onSkipClick = { /* Handle skip */ },
        onSeek = { viewModel.seekTo(it) },
        isPlaying = isPlaying,
        progress = progress,
        currentTime = currentTime,
        totalTime = totalTime
    )
}

@Preview
@Composable
fun MusicPlayerScreenPreview() {
    val mockSong = SongItem(
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
    )
    MusicPlayerComponent(
        song = mockSong,
        onBackClick = {},
        onPlayPauseClick = {},
        onRewindClick = {},
        onSkipClick = {},
        onSeek = {},
        isPlaying = true,
        progress = 0.5f,
        currentTime = "01:15",
        totalTime = "02:30"
    )
}
