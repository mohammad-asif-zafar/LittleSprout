package com.hathway.littlesprout.presentation.music.drums

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import littlesprout.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun DrumsContent(
    isMuted: Boolean,
    activePadId: String?,
    isRhythmMode: Boolean,
    rhythmStep: Int,
    rhythmSequence: List<DrumSound>,
    onDrumPress: (DrumSound, String) -> Unit,
    onDrumRelease: (String) -> Unit,
    onHomeClick: () -> Unit,
    onToggleMute: () -> Unit,
    onToggleRhythmMode: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var floatingParticleText by remember { mutableStateOf<String?>(null) }
    var floatingParticleX by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image
        Image(
            painter = painterResource(Res.drawable.drum_music_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Main Layout (Top Header + Drum Kit)
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

                // Drums Title Badge
                DrumsTitleBadge(
                    isRhythmMode = isRhythmMode,
                    rhythmStep = rhythmStep,
                    rhythmSequenceSize = rhythmSequence.size
                )

                // Controls: Rhythm Mode & Mute Toggle
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rhythm Mode Toggle Button
                    Surface(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable { onToggleRhythmMode() },
                        shape = CircleShape,
                        color = if (isRhythmMode) Color(0xFFFFD54F) else Color(0xFFE0E0E0),
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(Res.drawable.icon_star),
                                contentDescription = "Rhythm Mode",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    // Mute / Unmute Button
                    Surface(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable { onToggleMute() },
                        shape = CircleShape,
                        color = if (isMuted) Color(0xFFEF5350) else Color(0xFFAB47BC),
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(if (isMuted) Res.drawable.icon_pause else Res.drawable.icon_speaker),
                                contentDescription = if (isMuted) "Muted" else "Sound On",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Interactive Drum Set (Fills remaining screen height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 6.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                DrumKit(
                    activePadId = activePadId,
                    targetSound = if (isRhythmMode) rhythmSequence.getOrNull(rhythmStep) else null,
                    onDrumHit = { sound, padId, xOffset ->
                        onDrumPress(sound, padId)
                        coroutineScope.launch {
                            floatingParticleText = sound.displayName
                            floatingParticleX = xOffset
                            delay(600)
                            floatingParticleText = null
                        }
                    },
                    onDrumRelease = onDrumRelease
                )
            }
        }

        // 3. Parrot Mascot Overlaid on Top-Left
        val infiniteTransition = rememberInfiniteTransition()
        val parrotBounce by infiniteTransition.animateFloat(
            initialValue = -3f,
            targetValue = 3f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
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

        // Floating Particles on Drum Hit
        floatingParticleText?.let { text ->
            FloatingDrumParticle(
                text = text,
                offsetX = floatingParticleX
            )
        }
    }
}

@Composable
fun DrumsTitleBadge(
    isRhythmMode: Boolean,
    rhythmStep: Int,
    rhythmSequenceSize: Int
) {
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
                'D' to Color(0xFF007AFF),
                'r' to Color(0xFFFF3B30),
                'u' to Color(0xFFFFCC00),
                'm' to Color(0xFF34C759),
                's' to Color(0xFFAF52DE)
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

            if (isRhythmMode) {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "★ ${rhythmStep + 1}/$rhythmSequenceSize",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DrumKit(
    activePadId: String?,
    targetSound: DrumSound?,
    onDrumHit: (DrumSound, String, Float) -> Unit,
    onDrumRelease: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Metallic Stands in Background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Cymbal Stands
            val strokeWidth = 8.dp.toPx()
            val standColor = Color(0xFFB0BEC5)

            // Left Cymbal Stand
            drawLine(
                color = standColor,
                start = Offset(width * 0.18f, height * 0.25f),
                end = Offset(width * 0.22f, height * 0.85f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
            // Right Cymbal Stand
            drawLine(
                color = standColor,
                start = Offset(width * 0.82f, height * 0.25f),
                end = Offset(width * 0.78f, height * 0.85f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            // 1. Left Cymbal (Open Hat)
            DrumItemComponent(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.85f),
                imageRes = Res.drawable.drum_open_hat_item,
                sound = DrumSound.OPEN_HAT,
                isPressed = activePadId == "OPEN_HAT",
                isTarget = targetSound == DrumSound.OPEN_HAT,
                onHit = { onDrumHit(DrumSound.OPEN_HAT, "OPEN_HAT", -180f) },
                onRelease = { onDrumRelease("OPEN_HAT") }
            )

            // 2. Snare Drum (Blue)
            DrumItemComponent(
                modifier = Modifier
                    .weight(1.1f)
                    .fillMaxHeight(0.88f),
                imageRes = Res.drawable.drum_snare_item,
                sound = DrumSound.SNARE,
                isPressed = activePadId == "SNARE_LEFT",
                isTarget = targetSound == DrumSound.SNARE,
                onHit = { onDrumHit(DrumSound.SNARE, "SNARE_LEFT", -90f) },
                onRelease = { onDrumRelease("SNARE_LEFT") }
            )

            // 3. Central Kick Bass Drum (Red Star)
            DrumItemComponent(
                modifier = Modifier
                    .weight(1.35f)
                    .fillMaxHeight(1f),
                imageRes = Res.drawable.drum_kick_item,
                sound = DrumSound.KICK,
                isPressed = activePadId == "KICK_BASS",
                isTarget = targetSound == DrumSound.KICK,
                onHit = { onDrumHit(DrumSound.KICK, "KICK_BASS", 0f) },
                onRelease = { onDrumRelease("KICK_BASS") }
            )

            // 4. Claps Item (Purple Hands)
            DrumItemComponent(
                modifier = Modifier
                    .weight(1.1f)
                    .fillMaxHeight(0.88f),
                imageRes = Res.drawable.drum_claps_item,
                sound = DrumSound.CLAPS,
                isPressed = activePadId == "TOM_RIGHT",
                isTarget = targetSound == DrumSound.CLAPS,
                onHit = { onDrumHit(DrumSound.CLAPS, "TOM_RIGHT", 90f) },
                onRelease = { onDrumRelease("TOM_RIGHT") }
            )

            // 5. Right Cymbal (Closed Hat)
            DrumItemComponent(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.85f),
                imageRes = Res.drawable.drum_closed_hat_item,
                sound = DrumSound.CLOSED_HAT,
                isPressed = activePadId == "CLOSED_HAT",
                isTarget = targetSound == DrumSound.CLOSED_HAT,
                onHit = { onDrumHit(DrumSound.CLOSED_HAT, "CLOSED_HAT", 180f) },
                onRelease = { onDrumRelease("CLOSED_HAT") }
            )
        }
    }
}

@Composable
fun DrumItemComponent(
    modifier: Modifier = Modifier,
    imageRes: org.jetbrains.compose.resources.DrawableResource,
    sound: DrumSound,
    isPressed: Boolean,
    isTarget: Boolean,
    onHit: () -> Unit,
    onRelease: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val currentScale = if (isTarget && !isPressed) scale * pulseScale else scale

    Box(
        modifier = modifier
            .scale(currentScale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onHit()
                        tryAwaitRelease()
                        onRelease()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (isTarget) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.95f)
                    .clip(CircleShape)
                    .background(Color(0xFFFFD54F).copy(alpha = 0.4f))
            )
        }

        Image(
            painter = painterResource(imageRes),
            contentDescription = sound.displayName,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun FloatingDrumParticle(
    text: String,
    offsetX: Float
) {
    val animY = remember { Animatable(0f) }
    val animAlpha = remember { Animatable(1f) }

    LaunchedEffect(text) {
        launch { animY.animateTo(-120f, animationSpec = tween(600, easing = FastOutSlowInEasing)) }
        launch { animAlpha.animateTo(0f, animationSpec = tween(600, easing = LinearEasing)) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 120.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = "🥁 $text!",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFFFD54F),
            modifier = Modifier
                .offset(x = offsetX.dp, y = animY.value.dp)
                .scale(animAlpha.value)
        )
    }
}

@Preview
@Composable
fun DrumKitPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFF81C784))
                .padding(16.dp)
        ) {
            DrumKit(
                activePadId = "KICK_BASS",
                targetSound = DrumSound.SNARE,
                onDrumHit = { _, _, _ -> },
                onDrumRelease = {}
            )
        }
    }
}

@Preview
@Composable
fun DrumsContentPreview() {
    MaterialTheme {
        DrumsContent(
            isMuted = false,
            activePadId = null,
            isRhythmMode = false,
            rhythmStep = 0,
            rhythmSequence = listOf(DrumSound.KICK, DrumSound.SNARE, DrumSound.CLAPS),
            onDrumPress = { _, _ -> },
            onDrumRelease = {},
            onHomeClick = {},
            onToggleMute = {},
            onToggleRhythmMode = {}
        )
    }
}
