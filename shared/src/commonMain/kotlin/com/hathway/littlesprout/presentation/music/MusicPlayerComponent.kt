package com.hathway.littlesprout.presentation.music

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.SongItem
import kotlinx.coroutines.delay
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_clap_clap
import littlesprout.shared.generated.resources.icon_pause
import littlesprout.shared.generated.resources.icon_reween
import littlesprout.shared.generated.resources.icon_sing_along
import littlesprout.shared.generated.resources.icon_skip
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.music_playing_bg
import littlesprout.shared.generated.resources.playgreen
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
        // Background
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
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(categoryIcon),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = song.title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0D47A1)
                    )
                }

                Spacer(modifier = Modifier.size(56.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Illustration with Animation
            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = if (isPlaying) 1.05f else 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            val rotation by infiniteTransition.animateFloat(
                initialValue = -1f,
                targetValue = if (isPlaying) 1f else 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = LinearEasing), repeatMode = RepeatMode.Reverse
                )
            )

            Image(
                painter = painterResource(song.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.7f).height(200.dp).graphicsLayer(
                    scaleX = scale, scaleY = scale, rotationZ = rotation
                ),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lyrics Card - Middle with translucent background and sync
            LyricsSyncedCard(
                lyrics = song.lyrics,
                progress = progress,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(24.dp))

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
                currentTime = currentTime,
                totalTime = totalTime,
                progress = progress,
                onProgressChange = onSeek
            )
        }
    }
}

@Composable
fun LyricsSyncedCard(lyrics: String, progress: Float, modifier: Modifier = Modifier) {
    val lines = remember(lyrics) { lyrics.split("\n").filter { it.isNotBlank() } }
    val currentLineIndex = (progress * lines.size).toInt().coerceIn(0, lines.size - 1)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.4f), RoundedCornerShape(32.dp))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Show current line and next/prev for context
            val start = (currentLineIndex - 1).coerceAtLeast(0)
            val end = (currentLineIndex + 1).coerceAtMost(lines.size - 1)

            for (i in start..end) {
                val isCurrent = i == currentLineIndex
                Text(
                    text = lines[i].trim(),
                    fontSize = if (isCurrent) 28.sp else 20.sp,
                    fontWeight = if (isCurrent) FontWeight.ExtraBold else FontWeight.Bold,
                    color = if (isCurrent) Color(0xFF1B5E20) else Color(0xFF1B5E20).copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun PlaybackControls(
    isPlaying: Boolean, onRewind: () -> Unit, onPlayPause: () -> Unit, onSkip: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rewind
        ControlIcon(
            res = Res.drawable.icon_reween,
            contentDescription = "Rewind",
            onClick = onRewind,
            size = 80,
            animationType = AnimationType.SLIDE_LEFT
        )

        Spacer(modifier = Modifier.width(20.dp))

        // Play/Pause
        Box(contentAlignment = Alignment.Center) {
            ControlIcon(
                res = if (isPlaying) Res.drawable.playgreen else Res.drawable.icon_pause,
                contentDescription = if (isPlaying) "Pause" else "Play",
                onClick = onPlayPause,
                size = 110,
                animationType = AnimationType.PULSE
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        // Skip
        ControlIcon(
            res = Res.drawable.icon_skip,
            contentDescription = "Skip",
            onClick = onSkip,
            size = 80,
            animationType = AnimationType.SLIDE_RIGHT
        )
    }
}

enum class AnimationType {
    PULSE, SLIDE_LEFT, SLIDE_RIGHT, NONE
}

@Composable
fun ControlIcon(
    res: DrawableResource,
    contentDescription: String,
    onClick: () -> Unit,
    size: Int,
    animationType: AnimationType = AnimationType.NONE
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.8f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val offsetX by animateFloatAsState(
        targetValue = if (isPressed && animationType == AnimationType.SLIDE_LEFT) -20f
        else if (isPressed && animationType == AnimationType.SLIDE_RIGHT) 20f
        else 0f, animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Image(
        painter = painterResource(res),
        contentDescription = contentDescription,
        modifier = Modifier.size(size.dp).graphicsLayer(
            scaleX = scale, scaleY = scale, translationX = offsetX
        ).clickable(
            interactionSource = interactionSource, indication = null
        ) {
            isPressed = true
            onClick()
        })

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(150)
            isPressed = false
        }
    }
}

@Composable
fun PlaybackProgress(
    currentTime: String, totalTime: String, progress: Float, onProgressChange: (Float) -> Unit
) {
    // Animate progress smoothly
    val animatedProgress by animateFloatAsState(
        targetValue = progress, animationSpec = tween(durationMillis = 500, easing = LinearEasing)
    )

    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 48.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentTime,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
            Text(
                text = totalTime,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
        }

        Slider(
            value = animatedProgress,
            onValueChange = onProgressChange,
            modifier = Modifier.fillMaxWidth().height(40.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color(0xFF4CAF50),
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )
    }
}

// =================--- PREVIEW AREA ---=================

@Preview
@Composable
fun MusicPlayerComponentPreview() {
    // Create a mock song item matching your domain setup
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
