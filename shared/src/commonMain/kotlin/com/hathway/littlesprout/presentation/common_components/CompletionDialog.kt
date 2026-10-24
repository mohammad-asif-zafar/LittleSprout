package com.hathway.littlesprout.presentation.common_components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import littlesprout.shared.generated.resources.Orbidino_Cheerful_Jumping_Dino_Sticker
import littlesprout.shared.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

@Composable
fun CompletionDialog(
    categoryName: String,
    totalCount: Int,
    onPlayAgain: () -> Unit,
    onHome: () -> Unit,
    onNextCategory: (() -> Unit)? = null,
    onDismiss: () -> Unit = onHome
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        CompletionDialogContent(
            categoryName = categoryName,
            totalCount = totalCount,
            onPlayAgain = onPlayAgain,
            onHome = onHome,
            onNextCategory = onNextCategory
        )
    }
}

@Composable
fun CompletionDialogContent(
    categoryName: String,
    totalCount: Int,
    onPlayAgain: () -> Unit,
    onHome: () -> Unit,
    onNextCategory: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "StarAnimation")
    val starScale by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "StarPulse"
    )

    val mascotYOffset by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "MascotFloat"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .widthIn(max = 380.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp)),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 16.dp,
            border = androidx.compose.foundation.BorderStroke(4.dp, Color(0xFFE8F5E9))
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Mascot Image
                Image(
                    painter = painterResource(Res.drawable.Orbidino_Cheerful_Jumping_Dino_Sticker),
                    contentDescription = "Great Job Mascot",
                    modifier = Modifier
                        .size(130.dp)
                        .offset(y = mascotYOffset.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Title: Great job!
                Text(
                    text = "Great job!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF007AFF),
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.15f),
                            offset = Offset(2f, 2f),
                            blurRadius = 3f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Subtitle: You finished all X items!
                Text(
                    text = "You finished all $totalCount $categoryName!",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF455A64),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 3 Golden Stars ⭐ ⭐ ⭐
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⭐",
                        fontSize = 36.sp,
                        modifier = Modifier.scale(starScale * 0.9f)
                    )
                    Text(
                        text = "⭐",
                        fontSize = 46.sp,
                        modifier = Modifier.scale(starScale)
                    )
                    Text(
                        text = "⭐",
                        fontSize = 36.sp,
                        modifier = Modifier.scale(starScale * 0.9f)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Next Level / Category Button (if provided)
                    if (onNextCategory != null) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .clickable { onNextCategory() },
                            shape = RoundedCornerShape(24.dp),
                            color = Color(0xFF4CAF50),
                            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF2E7D32)),
                            shadowElevation = 4.dp
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Next Category ▶",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    // Play Again Button (Light Blue with refresh icon)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .clickable { onPlayAgain() },
                        shape = RoundedCornerShape(24.dp),
                        color = Color(0xFFE1F5FE),
                        border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF81D4FA)),
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "🔄 ", fontSize = 18.sp)
                            Text(
                                text = "Play Again",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF0288D1)
                            )
                        }
                    }

                    // Home Button (White with Home icon)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .clickable { onHome() },
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFCC80)),
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "🏠 ", fontSize = 18.sp)
                            Text(
                                text = "Home",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFE65100)
                            )
                        }
                    }
                }
            }
        }
    }
}
