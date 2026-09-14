package com.hathway.littlesprout.presentation.music

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.SongItem
import littlesprout.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun MusicPlayerComponent(
    song: SongItem,
    onBackClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onRewindClick: () -> Unit,
    onSkipClick: () -> Unit,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background
        Image(
            painter = painterResource(Res.drawable.music_playing_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable { onBackClick() }
                )
            }

            Text(
                text = song.title.replace("\n", " "),
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0D47A1),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Main Illustration
            Image(
                painter = painterResource(song.icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(250.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lyrics Card
            LyricsCard(
                mainText = "Old MacDonald had a farm",
                subText = "E-I-E-I-O!"
            )

            Spacer(modifier = Modifier.weight(1f))

            // Playback Controls
            PlaybackControls(
                isPlaying = isPlaying,
                onRewind = onRewindClick,
                onPlayPause = onPlayPauseClick,
                onSkip = onSkipClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Progress Bar
            PlaybackProgress(
                currentTime = "00:12",
                totalTime = "01:30",
                progress = 0.15f,
                onProgressChange = {}
            )
        }
    }
}

@Composable
fun LyricsCard(mainText: String, subText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9).copy(alpha = 0.9f), RoundedCornerShape(32.dp))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = mainText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )
            Text(
                text = subText,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1B5E20)
            )
        }
    }
}

@Composable
fun PlaybackControls(
    isPlaying: Boolean,
    onRewind: () -> Unit,
    onPlayPause: () -> Unit,
    onSkip: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rewind
        Image(
            painter = painterResource(Res.drawable.icon_reween),
            contentDescription = "Rewind",
            modifier = Modifier.size(80.dp).clickable { onRewind() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Play/Pause
        Image(
            painter = painterResource(if (isPlaying) Res.drawable.playgreen else Res.drawable.playgreen), // TODO: Add pause icon
            contentDescription = if (isPlaying) "Pause" else "Play",
            modifier = Modifier.size(100.dp).clickable { onPlayPause() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Skip
        Image(
            painter = painterResource(Res.drawable.icon_skip),
            contentDescription = "Skip",
            modifier = Modifier.size(80.dp).clickable { onSkip() }
        )
    }
}

@Composable
fun PlaybackProgress(
    currentTime: String,
    totalTime: String,
    progress: Float,
    onProgressChange: (Float) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 48.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = currentTime, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = totalTime, color = Color.White, fontWeight = FontWeight.Bold)
        }
        Slider(
            value = progress,
            onValueChange = onProgressChange,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color(0xFF81C784),
                inactiveTrackColor = Color.White.copy(alpha = 0.5f)
            )
        )
    }
}
