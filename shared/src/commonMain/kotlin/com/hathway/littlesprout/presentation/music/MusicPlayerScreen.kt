package com.hathway.littlesprout.presentation.music

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.littlesprout.domain.model.SongItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.img_music_twinkle

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
        title = "Twinkle Twinkle\nLittle Star",
        icon = Res.drawable.img_music_twinkle,
        backgroundColor = 0xFFE8F5E9,
        audioRes = "twinkle.mp3"
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
