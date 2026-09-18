package com.hathway.littlesprout.presentation.music

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.SongItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_clap_clap
import littlesprout.shared.generated.resources.icon_sing_along
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.music_playing_bg
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MusicPlayerComponent(
    song: SongItem,
    categoryIcon: DrawableResource = Res.drawable.icon_sing_along,
    categoryName: String = "Sing Along",
    onBackClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onRewindClick: () -> Unit,
    onSkipClick: () -> Unit,
    onSeek: (Float) -> Unit,
    isPlaying: Boolean,
    progress: Float,
    currentTime: String,
    totalTime: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.music_playing_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clip(RoundedCornerShape(50))
                        .background(Color.White.copy(alpha = 0.3f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Image(
                        painter = painterResource(categoryIcon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = categoryName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.size(56.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            val infiniteTransition = rememberInfiniteTransition()

            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = if (isPlaying) 1.04f else 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = if (isPlaying) 360f else 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(15000, easing = LinearEasing), repeatMode = RepeatMode.Restart
                )
            )
            Image(
                painter = painterResource(song.imageRes),
                contentDescription = song.title,
                modifier = Modifier.fillMaxWidth(0.65f).height(200.dp).graphicsLayer(
                    scaleX = scale, scaleY = scale, rotationZ = rotation
                ),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            LyricsSyncedCard(
                lyrics = song.lyrics, progress = progress, modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            PlaybackControls(
                isPlaying = isPlaying,
                onRewind = onRewindClick,
                onPlayPause = onPlayPauseClick,
                onSkip = onSkipClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            PlaybackProgress(
                currentTime = currentTime,
                totalTime = totalTime,
                progress = progress,
                onProgressChange = onSeek
            )
        }
    }
}

@Preview
@Composable
fun MusicPlayerComponentPreview() {

    val mockSong = SongItem(
        title = "Clap Clap",
        lyrics = "Clap, clap, clap your hands,\nClap them high, clap them low!\nTap, tap, tap your toes,\nTap them fast, then nice and slow!",
        imageRes = Res.drawable.icon_clap_clap,
        backgroundColorLong = 0xFFE1F5FE,
        audioPath = "audio_clap_clap.mp3"
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        MusicPlayerComponent(
            song = mockSong,
            onBackClick = {},
            onPlayPauseClick = {},
            onRewindClick = {},
            onSkipClick = {},
            onSeek = {},
            isPlaying = true,
            progress = 0.35f,
            currentTime = "0:45",
            totalTime = "2:30"
        )
    }
}
