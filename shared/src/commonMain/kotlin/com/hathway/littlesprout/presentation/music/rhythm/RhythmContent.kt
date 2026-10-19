package com.hathway.littlesprout.presentation.music.rhythm

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.presentation.music.drums.DrumKit
import com.hathway.littlesprout.presentation.music.drums.DrumSound
import kotlinx.coroutines.delay
import littlesprout.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun RhythmContent(
    currentMode: RhythmMode,
    isMuted: Boolean,
    activePadId: String?,
    isShowingDemo: Boolean,
    copyStep: Int,
    copySequence: List<DrumSound>,
    demoHighlightedSound: DrumSound?,
    currentTrackName: String,
    onModeSelect: (RhythmMode) -> Unit,
    onNextTrack: () -> Unit,
    onDrumPress: (DrumSound, String) -> Unit,
    onDrumRelease: (String) -> Unit,
    onHomeClick: () -> Unit,
    onToggleMute: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image using rhythm_bg.png
        Image(
            painter = painterResource(Res.drawable.rhythm_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home Button
                Image(
                    painter = painterResource(Res.drawable.icon_home),
                    contentDescription = "Home",
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .clickable { onHomeClick() }
                )

                // Rhythm Title Badge
                RhythmTitleBadge()

                // Mode Selector Tabs (Play, Copy, Dance) & Sound Mute
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RhythmModeTab(title = "🥁 Play", isSelected = currentMode == RhythmMode.PLAY) {
                        onModeSelect(RhythmMode.PLAY)
                    }
                    RhythmModeTab(title = "👂 Copy", isSelected = currentMode == RhythmMode.COPY) {
                        onModeSelect(RhythmMode.COPY)
                    }
                    RhythmModeTab(title = "💃 Dance", isSelected = currentMode == RhythmMode.DANCE) {
                        onModeSelect(RhythmMode.DANCE)
                    }

                    // Mute Button
                    Surface(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .clickable { onToggleMute() },
                        shape = CircleShape,
                        color = if (isMuted) Color(0xFFEF5350) else Color(0xFFAB47BC),
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(if (isMuted) Res.drawable.icon_pause else Res.drawable.icon_speaker),
                                contentDescription = "Sound",
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Main Content Area based on Mode
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                when (currentMode) {
                    RhythmMode.PLAY, RhythmMode.COPY -> {
                        val target = if (currentMode == RhythmMode.COPY) {
                            if (isShowingDemo) demoHighlightedSound else copySequence.getOrNull(copyStep)
                        } else null

                        DrumKit(
                            activePadId = activePadId,
                            targetSound = target,
                            onDrumHit = { sound, padId, _ ->
                                if (!isShowingDemo) {
                                    onDrumPress(sound, padId)
                                }
                            },
                            onDrumRelease = onDrumRelease
                        )
                    }
                    RhythmMode.DANCE -> {
                        DancePartyView(
                            currentTrackName = currentTrackName,
                            isMuted = isMuted,
                            onNextTrack = onNextTrack
                        )
                    }
                }
            }
        }

        // Parrot Mascot
        val infiniteTransition = rememberInfiniteTransition(label = "ParrotBounce")
        val parrotBounce by infiniteTransition.animateFloat(
            initialValue = -5f,
            targetValue = 5f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "ParrotOffsetY"
        )

        Image(
            painter = painterResource(Res.drawable.bird_parrot),
            contentDescription = "Mascot",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 72.dp, top = 8.dp)
                .size(76.dp)
                .offset(y = parrotBounce.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun RhythmTitleBadge() {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFFFB300),
        border = androidx.compose.foundation.BorderStroke(3.dp, Color(0xFF8D6E63)),
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val titleLetters = listOf(
                'R' to Color(0xFF007AFF),
                'h' to Color(0xFFFF3B30),
                'y' to Color(0xFFFFCC00),
                't' to Color(0xFF34C759),
                'h' to Color(0xFFAF52DE),
                'm' to Color(0xFFFF2D55)
            )

            titleLetters.forEach { (char, color) ->
                Text(
                    text = char.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = color,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.3f),
                            offset = Offset(2f, 3f),
                            blurRadius = 2f
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun RhythmModeTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0xFFFFD54F) else Color.White.copy(alpha = 0.85f),
        shadowElevation = 4.dp
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color(0xFF3E2723) else Color(0xFF455A64),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}

private val orbidinoPoses = listOf(
    Res.drawable.Orbidino_Cheerful_Jumping_Dino_Sticker,
    Res.drawable.Orbidino_Cheerful_Lime_Baby_Dinosaur,
    Res.drawable.Orbidino_Cheerful_Waving_Dino_Mascot,
    Res.drawable.Orbidino_Cool_Lime_Dinosaur_Mascot,
    Res.drawable.Orbidino_Cute_Lime_Green_Dino_Dragon_Sticker,
    Res.drawable.Orbidino_Glossy_Winking_Dinosaur_Mascot,
    Res.drawable.Orbidino_Joyful__2_Jumping_Green_Dino_Mascot,
    Res.drawable.Orbidino_Joyful_Green_Baby_Dragon_Mascot,
    Res.drawable.Orbidino_Joyful_Jumping_Green_Dino_Mascot,
    Res.drawable.Orbidino_Joyful_Jumping_Green_Dinosaur_Mascot,
    Res.drawable.Orbidino_Joyful_Lime_Dino_Mascot,
    Res.drawable.Orbidino_Joyful_Lime_Dinosaur_Mascot,
    Res.drawable.Orbidino_Joyful_Singing_Dino_Sticker,
    Res.drawable.Orbidino_Laughing_Lime_Dinosaur_Mascot,
    Res.drawable.Orbidino_Winking_Green_Dinosaur_Mascot
)

@Composable
fun DancePartyView(
    currentTrackName: String,
    isMuted: Boolean,
    onNextTrack: () -> Unit
) {
    var poseIndex by remember { mutableStateOf(0) }

    LaunchedEffect(isMuted) {
        while (true) {
            delay(1400L)
            poseIndex = (poseIndex + 1) % orbidinoPoses.size
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "DanceMotion")
    val danceScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "DanceScale"
    )

    val danceRotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "DanceRotation"
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val totalHeight = maxHeight

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Track Banner
            Surface(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .clickable { onNextTrack() },
                shape = RoundedCornerShape(30.dp),
                color = Color(0xFFFFB300),
                shadowElevation = 6.dp,
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFF8E1))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "🎶 Now Playing: $currentTrackName",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        )
                    )
                    Text(
                        text = "⏭️ Next Song",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF3E2723)
                    )
                }
            }

            // Orbidino Mascot Container occupying ~70% of available content height
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(totalHeight * 0.72f)
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(
                    targetState = orbidinoPoses[poseIndex],
                    animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
                    label = "PoseCrossfade"
                ) { poseDrawable ->
                    Image(
                        painter = painterResource(poseDrawable),
                        contentDescription = "Dancing Orbidino Mascot",
                        modifier = Modifier
                            .fillMaxHeight()
                            .scale(danceScale)
                            .rotate(danceRotation),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 1280,
    heightDp = 800
)
@Preview
@Composable
fun RhythmContentPreview() {
    MaterialTheme {
        RhythmContent(
            currentMode = RhythmMode.DANCE,
            isMuted = false,
            activePadId = null,
            isShowingDemo = false,
            copyStep = 0,
            copySequence = listOf(DrumSound.KICK, DrumSound.SNARE),
            demoHighlightedSound = null,
            currentTrackName = "Dance Track 1",
            onModeSelect = {},
            onNextTrack = {},
            onDrumPress = { _, _ -> },
            onDrumRelease = {},
            onHomeClick = {},
            onToggleMute = {}
        )
    }
}
