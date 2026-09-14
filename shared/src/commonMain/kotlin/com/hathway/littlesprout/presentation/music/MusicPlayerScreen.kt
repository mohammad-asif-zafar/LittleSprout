package com.hathway.littlesprout.presentation.music

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hathway.littlesprout.domain.model.SongItem

@Composable
fun MusicPlayerScreen(
    viewModel: MusicViewModel,
    song: SongItem,
    onBackClick: () -> Unit
) {
    val isPlaying by viewModel.isPlaying.collectAsState()

    LaunchedEffect(song) {
        viewModel.playSong(song)
    }

    MusicPlayerComponent(
        song = song,
        onBackClick = onBackClick,
        onPlayPauseClick = { viewModel.togglePlayPause() },
        onRewindClick = { /* Handle rewind */ },
        onSkipClick = { /* Handle skip */ },
        isPlaying = isPlaying
    )
}
