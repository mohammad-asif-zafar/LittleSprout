package com.hathway.littlesprout.presentation.alphabet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.littlesprout.domain.model.AlphabetItem
import com.hathway.littlesprout.presentation.common_components.ColoredText
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
    isPlaying: Boolean,
    onBackClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlaySoundClick: (String) -> Unit
) {
    var swipeOffset by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier.fillMaxSize()

            .pointerInput(currentItem) {
                detectHorizontalDragGestures(onDragEnd = {
                    if (swipeOffset > 150f && isPreviousEnabled) {
                        onPreviousClick()
                    } else if (swipeOffset < -150f && isNextEnabled) {
                        onNextClick()
                    }
                    swipeOffset = 0f
                }, onHorizontalDrag = { change, dragAmount ->
                    change.consume()
                    swipeOffset += dragAmount
                })
            }) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })

                Box(
                    modifier = Modifier.size(56.dp)
                        .clickable { currentItem.audio?.let { onPlaySoundClick(it) } } // Passes the string out
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

            Card(
                modifier = Modifier.weight(1f).fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {

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
                        Image(
                            painter = painterResource(targetItem.letterImage),
                            contentDescription = "Letter ${targetItem.letter}",
                            modifier = Modifier.height(120.dp).fillMaxWidth(),
                            contentScale = ContentScale.Fit
                        )

                        Image(
                            painter = painterResource(targetItem.objectImage),
                            contentDescription = targetItem.description,
                            modifier = Modifier.height(180.dp).fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .wrapContentHeight() // FIXED: Allows the layout to expand naturally for large fonts
                                .defaultMinSize(minHeight = 80.dp) // Ensures a stable minimum baseline container height
                                .padding(bottom = 8.dp), contentAlignment = Alignment.Center
                        ) {
                            ColoredText(
                                text = targetItem.description.uppercase()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isPreviousEnabled) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_left),
                        contentDescription = "Previous",
                        modifier = Modifier.size(72.dp).clickable { onPreviousClick() })
                } else {
                    Spacer(modifier = Modifier.size(72.dp))
                }

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

@Preview
@Composable
fun AlphabetScreenPreview() {
    MaterialTheme {
        val mockItem = AlphabetItem(
            letter = "Aa",
            letterImage = Res.drawable.aa,
            objectImage = Res.drawable.img_apple,
            description = "A for Apple",
            audio = "a_apple"
        )

        AlphabetContent(
            currentItem = mockItem,
            isPreviousEnabled = false,
            isNextEnabled = true,
            onBackClick = {},
            onPreviousClick = {},
            onNextClick = {},
            isPlaying = false,
            onPlaySoundClick = {},
        )
    }
}