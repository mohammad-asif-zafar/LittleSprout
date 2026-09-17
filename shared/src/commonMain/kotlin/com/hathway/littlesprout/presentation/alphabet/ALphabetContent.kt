package com.hathway.littlesprout.presentation.alphabet

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.AlphabetItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.aa
import littlesprout.shared.generated.resources.icon_repeat
import littlesprout.shared.generated.resources.img_alphabet_bg
import littlesprout.shared.generated.resources.img_apple
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_sweep_left
import littlesprout.shared.generated.resources.img_sweep_right
import org.jetbrains.compose.resources.painterResource

@Composable
fun AlphabetContent(
    currentItem: AlphabetItem,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean,
    isPlaying: Boolean, // Added to track current audio playback state
    onBackClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlaySoundClick: (String) -> Unit // Exposes the item string context on click
) {
    // Tracks swipe drag offset accumulation to determine child page changes
    var swipeOffset by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier.fillMaxSize()
            // 1. SWIPE GESTURES BLOCK: Horizontal swipes to navigate securely
            .pointerInput(currentItem) { // Keyed to currentItem to refresh swipe states cleanly on change
                detectHorizontalDragGestures(onDragEnd = {
                    if (swipeOffset > 150f && isPreviousEnabled) {
                        onPreviousClick()
                    } else if (swipeOffset < -150f && isNextEnabled) {
                        onNextClick()
                    }
                    swipeOffset = 0f // Clear current state threshold
                }, onHorizontalDrag = { change, dragAmount ->
                    change.consume()
                    swipeOffset += dragAmount
                })
            }) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.img_alphabet_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Action Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })

                // FIXED: Speaker / Repeat Icon with dynamic Canvas red slash logic on top layer
                Box(
                    modifier = Modifier.size(56.dp)
                        .clickable { currentItem.audio?.let { onPlaySoundClick(it) } } // Passes the string out
                        .drawWithContent {
                            drawContent()
                            if (!isPlaying) {
                                // Draws an anti-aliased crosswise diagonal cancellation bar
                                drawLine(
                                    color = Color.Red,
                                    start = Offset(size.width * 0.25f, size.height * 0.25f),
                                    end = Offset(size.width * 0.75f, size.height * 0.75f),
                                    strokeWidth = 4.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }, contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.icon_repeat),
                        contentDescription = if (isPlaying) "Stop Audio" else "Play Audio",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Flashcard Container
            Card(
                modifier = Modifier.weight(1f).fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                // 2. ENTRANCE ANIMATION WRAPPER: Animates letters and objects inside the container dynamically
                AnimatedContent(
                    targetState = currentItem, transitionSpec = {
                        (fadeIn(animationSpec = tween(400)) + scaleIn(
                            initialScale = 0.88f, animationSpec = tween(400)
                        )) togetherWith (fadeOut(animationSpec = tween(300)) + scaleOut(
                            targetScale = 0.95f, animationSpec = tween(300)
                        ))
                    }, modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { targetItem ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Display Letter Image
                        Image(
                            painter = painterResource(targetItem.letterImage),
                            contentDescription = "Letter ${targetItem.letter}",
                            modifier = Modifier.height(120.dp).fillMaxWidth(),
                            contentScale = ContentScale.Fit
                        )

                        // Display Object Image
                        Image(
                            painter = painterResource(targetItem.objectImage),
                            contentDescription = targetItem.description,
                            modifier = Modifier.height(180.dp).fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Fit
                        )

                        // Text Description & Audio Trigger Button
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .clickable { targetItem.audio?.let { onPlaySoundClick(it) } }
                                .padding(horizontal = 24.dp, vertical = 12.dp)) {
                            Text(
                                text = targetItem.description,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("🔊", fontSize = 20.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Controls (Previous / Next)
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous Button
                if (isPreviousEnabled) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_left),
                        contentDescription = "Previous",
                        modifier = Modifier.size(72.dp).clickable { onPreviousClick() })
                } else {
                    Spacer(modifier = Modifier.size(72.dp))
                }

                // Next Button
                if (isNextEnabled) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_right),
                        contentDescription = "Next",
                        modifier = Modifier.size(72.dp).clickable { onNextClick() })
                } else {
                    Spacer(modifier = Modifier.size(72.dp))
                }
            }
        }
    }
}


// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun AlphabetScreenPreview() {
    MaterialTheme {
        // Supplying static mock data so the Layout Engine renders immediately without actual architecture errors
        val mockItem = AlphabetItem(
            letter = "Aa",
            letterImage = Res.drawable.aa,
            objectImage = Res.drawable.img_apple,
            description = "A for Apple",
            audio = "a_apple"
        )

        AlphabetContent(
            currentItem = mockItem,
            isPreviousEnabled = false, // Emulate first card logic
            isNextEnabled = true,
            onBackClick = {},
            onPreviousClick = {},
            onNextClick = {},
            isPlaying = false,
            onPlaySoundClick = {},
        )
    }
}