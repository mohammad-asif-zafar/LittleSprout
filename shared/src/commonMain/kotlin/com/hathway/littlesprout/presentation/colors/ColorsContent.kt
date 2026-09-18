package com.hathway.littlesprout.presentation.colors

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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.ColorItem
import com.hathway.littlesprout.presentation.common_components.ColoredText
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_repeat
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_color_bg
import littlesprout.shared.generated.resources.img_color_pink
import littlesprout.shared.generated.resources.img_sweep_left
import littlesprout.shared.generated.resources.img_sweep_right
import org.jetbrains.compose.resources.painterResource

@Composable
fun ColorsContent(
    currentItem: ColorItem,
    currentIndex: Int,
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
        Image(
            painter = painterResource(Res.drawable.img_color_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })
                Box(
                    modifier = Modifier.size(64.dp).clickable { onPlaySoundClick() }
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

            Box(
                modifier = Modifier.fillMaxSize().weight(1f).pointerInput(currentItem) {
                    detectHorizontalDragGestures(onDragEnd = {
                        if (swipeOffset > 150f && currentIndex > 0) {
                            onPreviousClick()
                        } else if (swipeOffset < -150f && currentIndex < totalItemsCount - 1) {
                            onNextClick()
                        }
                        swipeOffset = 0f
                    }, onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        swipeOffset += dragAmount
                    })
                }) {

                Box(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
                        .clip(RoundedCornerShape(40.dp)).background(Color.White.copy(alpha = 0.9f))
                ) {
                    AnimatedContent(
                        targetState = currentItem, transitionSpec = {
                            (fadeIn(animationSpec = tween(400)) + scaleIn(
                                initialScale = 0.85f, animationSpec = tween(400)
                            )) togetherWith (fadeOut(animationSpec = tween(300)) + scaleOut(
                                targetScale = 0.95f, animationSpec = tween(300)
                            ))
                        }, modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) { targetColor ->
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(targetColor.colorImage),
                                contentDescription = null,
                                modifier = Modifier.size(240.dp),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                    .defaultMinSize(minHeight = 80.dp).padding(bottom = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                ColoredText(
                                    text = targetColor.name.uppercase(),
                                    singleColor = Color(targetColor.colorCode)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentIndex > 0) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_left),
                        contentDescription = "Previous",
                        modifier = Modifier.size(72.dp).clickable { onPreviousClick() })
                } else {
                    Spacer(modifier = Modifier.size(72.dp))
                }

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
                if (currentIndex < totalItemsCount - 1) {
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

@Preview
@Composable
fun ColorsScreenPreview() {
    MaterialTheme {
        val mockColor = ColorItem(
            name = "Pink",
            colorImage = Res.drawable.img_color_pink,
            colorCode = 0xFFFF0000,
            soundRes = "red_sound"
        )

        ColorsContent(
            currentItem = mockColor,
            currentIndex = 1,
            totalItemsCount = 3,
            isPlaying = false,
            onBackClick = {},
            onHomeClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onPlaySoundClick = {})
    }
}