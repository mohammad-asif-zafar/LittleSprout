package com.hathway.littlesprout.presentation.common_components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.bird_hornbill
import littlesprout.shared.generated.resources.common_bg
import littlesprout.shared.generated.resources.common_card_bg
import littlesprout.shared.generated.resources.icon_home
import littlesprout.shared.generated.resources.icon_repeat
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_sweep_left
import littlesprout.shared.generated.resources.img_sweep_right
import org.jetbrains.compose.resources.painterResource

@Composable
fun CommonContent(
    currentItem: CommonItem,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean,
    isPlaying: Boolean,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlaySoundClick: (String) -> Unit
) {
    var swipeOffset by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier.fillMaxSize().pointerInput(currentItem) {
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
            painter = painterResource(Res.drawable.common_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(64.dp).clickable { onBackClick() })

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(64.dp)
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
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(0.98f),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(Res.drawable.common_card_bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                AnimatedContent(
                    targetState = currentItem, transitionSpec = {
                        (fadeIn(animationSpec = tween(400)) + scaleIn(
                            initialScale = 0.88f, animationSpec = tween(400)
                        )) togetherWith (fadeOut(animationSpec = tween(300)) + scaleOut(
                            targetScale = 0.95f, animationSpec = tween(300)
                        ))
                    }, modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { targetItem ->
                    val infiniteTransition = rememberInfiniteTransition(label = "CommonAnimations")
                    val bounceY by infiniteTransition.animateFloat(
                        initialValue = -6f,
                        targetValue = 6f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(900, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "CommonBounce"
                    )
                    val sparkleScale by infiniteTransition.animateFloat(
                        initialValue = 0.8f,
                        targetValue = 1.2f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(700, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "SparkleScale"
                    )

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "✨",
                            fontSize = 24.sp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 16.dp, top = 16.dp)
                                .scale(sparkleScale)
                        )
                        Text(
                            text = "⭐",
                            fontSize = 28.sp,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(end = 16.dp, top = 16.dp)
                                .scale(sparkleScale)
                        )

                        Column(
                            modifier = Modifier.fillMaxSize().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(0.95f)
                                    .padding(top = 16.dp)
                                    .offset(y = bounceY.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(targetItem.objectImage),
                                    contentDescription = targetItem.description,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Box(
                                modifier = Modifier.fillMaxWidth().height(80.dp).padding(bottom = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                ColoredText(
                                    text = targetItem.description.uppercase()
                                )
                            }
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
                        modifier = Modifier.size(85.dp).clickable { onPreviousClick() })
                } else {
                    Spacer(modifier = Modifier.size(85.dp))
                }
                if (isNextEnabled) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_right),
                        contentDescription = "Next",
                        modifier = Modifier.size(85.dp).clickable { onNextClick() })
                } else {
                    Spacer(modifier = Modifier.size(85.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun CommonContentPreview() {
    MaterialTheme {
        val mockItem = CommonItem(
            letter = "Pp",
            letterImage = Res.drawable.bird_hornbill,
            objectImage = Res.drawable.bird_hornbill,
            description = "Parrot",
            audio = "parrot"
        )

        CommonContent(
            currentItem = mockItem,
            isPreviousEnabled = true,
            isNextEnabled = true,
            onBackClick = {},
            onHomeClick = {},
            onPreviousClick = {},
            onNextClick = {},
            isPlaying = false,
            onPlaySoundClick = {},
        )
    }
}
