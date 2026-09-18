package com.hathway.littlesprout.presentation.numbers

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.NumberItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_home
import littlesprout.shared.generated.resources.icon_repeat
import littlesprout.shared.generated.resources.icon_speaker
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_hand_two
import littlesprout.shared.generated.resources.img_number_item
import littlesprout.shared.generated.resources.img_sweep_left
import littlesprout.shared.generated.resources.img_sweep_right
import littlesprout.shared.generated.resources.img_two
import org.jetbrains.compose.resources.painterResource

@Composable
fun NumberDetailContent(
    currentItem: NumberItem,
    selectedIndex: Int,
    totalItemsCount: Int,
    isPlaying: Boolean,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlaySoundClick: () -> Unit
) {
    var swipeOffset by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.img_number_item),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Content
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })

                // Home Button (Top Right Corner of the Screen)
                Image(
                    painter = painterResource(Res.drawable.icon_home),
                    contentDescription = "Home",
                    modifier = Modifier.size(56.dp).clickable { onHomeClick() })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main White Card Container with Swipe Support
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.White.copy(alpha = 0.9f))
                    .pointerInput(currentItem) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                if (swipeOffset > 150f && selectedIndex > 0) {
                                    onPreviousClick()
                                } else if (swipeOffset < -150f && selectedIndex < totalItemsCount - 1) {
                                    onNextClick()
                                }
                                swipeOffset = 0f
                            },
                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()
                                swipeOffset += dragAmount
                            }
                        )
                    }
            ) {
                // FIXED: Speaker Icon with dynamic Canvas red slash logic on top layer
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(20.dp)
                        .size(64.dp)
                        .clickable { onPlaySoundClick() }
                        .drawWithContent {
                            drawContent()
                            if (!isPlaying) {
                                drawLine(
                                    color = Color.Red,
                                    start = Offset(size.width * 0.25f, size.height * 0.25f),
                                    end = Offset(size.width * 0.75f, size.height * 0.75f),
                                    strokeWidth = 4.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.icon_repeat),
                        contentDescription = if (isPlaying) "Stop Audio" else "Play Audio",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Core Card Content Layout
                AnimatedContent(
                    targetState = currentItem,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(400)) + scaleIn(
                            initialScale = 0.88f, animationSpec = tween(400)
                        )) togetherWith (fadeOut(animationSpec = tween(300)) + scaleOut(
                            targetScale = 0.95f, animationSpec = tween(300)
                        ))
                    },
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { targetItem ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // 3D Number Card
                        Image(
                            painter = painterResource(targetItem.gridImage),
                            contentDescription = null,
                            modifier = Modifier.size(140.dp),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Hand/Fingers Icon
                        Image(
                            painter = painterResource(targetItem.detailImage),
                            contentDescription = null,
                            modifier = Modifier.size(180.dp),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Descriptive Text
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFFFF5252), fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(targetItem.name)
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFF1565C0), fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(" finger")
                                if (targetItem.value != 1) append("s")
                            }
                        }
                        Text(text = annotatedString, fontSize = 28.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Navigation and Badge Row (Outside the card)
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Arrow
                if (selectedIndex > 0) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_left),
                        contentDescription = "Previous",
                        modifier = Modifier.size(72.dp).clickable { onPreviousClick() })
                } else {
                    Spacer(modifier = Modifier.size(72.dp))
                }

                // Center Bottom Badge
                Surface(
                    modifier = Modifier.height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Great job!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E88E5)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("⭐", fontSize = 24.sp)
                    }
                }

                // Right Arrow
                if (selectedIndex < totalItemsCount - 1) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_right),
                        contentDescription = "Next",
                        modifier = Modifier.size(72.dp).clickable { onNextClick() })
                } else {
                    Spacer(modifier = Modifier.size(72.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun NumberDetailScreenPreview() {
    MaterialTheme {
        // Static layout mockup item mapping Number 2 ("Two") to simulate a middle state index
        val mockNumberItem = NumberItem(
            value = 2,
            name = "Two",
            gridImage = Res.drawable.img_two,
            detailImage = Res.drawable.img_hand_two,
            fingerText = "Two fingers",
            soundRes = "audio_two.mp3",
        )
        NumberDetailContent(
            currentItem = mockNumberItem,
            selectedIndex = 1, // Activates both previous and next sweep graphic icons
            totalItemsCount = 10,
            isPlaying = false,
            onBackClick = {},
            onHomeClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onPlaySoundClick = {})
    }
}
