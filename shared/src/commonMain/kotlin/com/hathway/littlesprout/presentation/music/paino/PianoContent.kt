package com.hathway.littlesprout.presentation.music.paino

import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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
fun PianoContent(
    notes: List<PianoNote>,
    isMuted: Boolean,
    activeNoteIndex: Int?,
    isSongMode: Boolean,
    songStep: Int,
    songSequence: List<Int>,
    onNotePress: (Int) -> Unit,
    onNoteRelease: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onToggleMute: () -> Unit,
    onToggleSongMode: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var floatingParticleNote by remember { mutableStateOf<String?>(null) }
    var floatingParticleX by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image (Fills 100% of screen bounds, NO BLACK BARS!)
        Image(
            painter = painterResource(Res.drawable.paino_music_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // LANDSCAPE / UNIFIED HORIZONTAL LAYOUT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Header Bar
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

                // Title Badge: "Piano"
                PianoTitleBadge(
                    isSongMode = isSongMode,
                    songStep = songStep,
                    songSequenceSize = songSequence.size
                )

                // Controls: Song Mode & Mute Toggle
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable { onToggleSongMode() },
                        shape = CircleShape,
                        color = if (isSongMode) Color(0xFFFFD54F) else Color(0xFFE0E0E0),
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(Res.drawable.icon_star),
                                contentDescription = "Song Mode",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

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

            // Piano Chassis (Fills all remaining height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 4.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                PianoChassis(
                    notes = notes,
                    activeNoteIndex = activeNoteIndex,
                    highlightedKeyIndex = if (isSongMode) songSequence.getOrNull(songStep) else null,
                    onNotePress = { index ->
                        onNotePress(index)
                        coroutineScope.launch {
                            floatingParticleNote = notes.getOrNull(index)?.solfege
                            floatingParticleX = (index - 3) * 60f
                            delay(600)
                            floatingParticleNote = null
                        }
                    },
                    onNoteRelease = onNoteRelease
                )
            }
        }

        // Mascot Overlay on Top Left Branch
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

        // Floating Musical Note Particles on Tap
        floatingParticleNote?.let { solfege ->
            FloatingNoteEffect(
                solfege = solfege,
                offsetX = floatingParticleX
            )
        }
    }
}

@Composable
fun PianoTitleBadge(
    isSongMode: Boolean,
    songStep: Int,
    songSequenceSize: Int
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
                'P' to Color(0xFFFF3B30),
                'i' to Color(0xFFFFCC00),
                'a' to Color(0xFF34C759),
                'n' to Color(0xFF007AFF),
                'o' to Color(0xFFAF52DE)
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

            if (isSongMode) {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "★ ${songStep + 1}/$songSequenceSize",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun PianoChassis(
    notes: List<PianoNote>,
    activeNoteIndex: Int?,
    highlightedKeyIndex: Int?,
    onNotePress: (Int) -> Unit,
    onNoteRelease: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(36.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF3D00),
                        Color(0xFFFF3D00),
                        Color(0xFFFFC107),
                        Color(0xFFFFB300)
                    )
                )
            )
            .border(4.dp, Color(0xFFFF6D00), RoundedCornerShape(36.dp))
            .padding(top = 16.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFFFF8E1))
                .border(2.dp, Color(0xFFFFD54F), RoundedCornerShape(24.dp))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                notes.forEachIndexed { index, note ->
                    val isPressed = activeNoteIndex == index
                    val isHighlighted = highlightedKeyIndex == index

                    PianoKeyButton(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        note = note,
                        isPressed = isPressed,
                        isHighlighted = isHighlighted,
                        onPress = { onNotePress(index) },
                        onRelease = { onNoteRelease(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun PianoKeyButton(
    modifier: Modifier = Modifier,
    note: PianoNote,
    isPressed: Boolean,
    isHighlighted: Boolean,
    onPress: () -> Unit,
    onRelease: () -> Unit
) {
    val offsetY by animateDpAsState(
        targetValue = if (isPressed) 10.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val infiniteTransition = rememberInfiniteTransition()
    val guidePulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val currentScale = if (isHighlighted && !isPressed) scale * guidePulse else scale

    Box(
        modifier = modifier
            .offset(y = offsetY)
            .scale(currentScale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onPress()
                        tryAwaitRelease()
                        onRelease()
                    }
                )
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        note.keyDrawable?.let { drawableRes ->
            Image(
                painter = painterResource(drawableRes),
                contentDescription = note.solfege,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                note.keyColor.copy(alpha = 0.9f),
                                note.keyColor,
                                note.keyColor.copy(alpha = 0.85f)
                            )
                        )
                    )
                    .border(
                        width = if (isHighlighted) 4.dp else 2.dp,
                        color = if (isHighlighted) Color.White else Color.Black.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            if (isHighlighted) {
                Text(
                    text = "★",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Text(
                text = note.solfege,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(2f, 3f),
                        blurRadius = 3f
                    )
                )
            )
        }
    }
}

@Composable
fun FloatingNoteEffect(
    solfege: String,
    offsetX: Float
) {
    val animY = remember { Animatable(0f) }
    val animAlpha = remember { Animatable(1f) }

    LaunchedEffect(solfege) {
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
            text = "🎵 $solfege",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFFFD54F),
            modifier = Modifier
                .offset(x = offsetX.dp, y = animY.value.dp)
                .scale(animAlpha.value)
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 1280,
    heightDp = 800
)
@Preview
@Composable
fun PianoContentPreview() {
    MaterialTheme {
        val sampleNotes = listOf(
            PianoNote("C", "Do", "piano_c.wav", Color(0xFFFF3B30), Res.drawable.paino_red),
            PianoNote("D", "Re", "piano_d.wav", Color(0xFFFF9500), Res.drawable.paino_oranage),
            PianoNote("E", "Mi", "piano_e.wav", Color(0xFFFFCC00), Res.drawable.paino_yellow),
            PianoNote("F", "Fa", "piano_f.wav", Color(0xFF34C759), Res.drawable.paino_green),
            PianoNote("G", "Sol", "piano_g.wav", Color(0xFF007AFF), Res.drawable.paino_blue),
            PianoNote("A", "La", "piano_a.wav", Color(0xFFAF52DE), Res.drawable.paino_voilet),
            PianoNote("B", "Ti", "piano_b.wav", Color(0xFFFF2D55), Res.drawable.paino_red)
        )
        PianoContent(
            notes = sampleNotes,
            isMuted = false,
            activeNoteIndex = null,
            isSongMode = false,
            songStep = 0,
            songSequence = listOf(0, 0, 4, 4, 5, 5, 4),
            onNotePress = {},
            onNoteRelease = {},
            onHomeClick = {},
            onToggleMute = {},
            onToggleSongMode = {}
        )
    }
}
