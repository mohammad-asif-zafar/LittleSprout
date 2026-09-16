package com.hathway.littlesprout.presentation.colors

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.ColorItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_home
import littlesprout.shared.generated.resources.icon_speaker
import littlesprout.shared.generated.resources.img_apple
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_color_bg
import littlesprout.shared.generated.resources.img_color_pink
import littlesprout.shared.generated.resources.img_sweep_left
import littlesprout.shared.generated.resources.img_sweep_right
import org.jetbrains.compose.resources.painterResource

@Composable
fun ColorsContent(
    currentItem: ColorItem, // Assuming your model structure is named ColorItem
    currentIndex: Int,
    totalItemsCount: Int,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlaySoundClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.img_color_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Content
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button (Left Corner)
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() }
                )

                // FIXED: Replaced the Home button with the Speaker Icon in the Top Right Corner
                Image(
                    painter = painterResource(Res.drawable.icon_speaker),
                    contentDescription = "Play Audio",
                    modifier = Modifier
                        .size(56.dp)
                        .clickable { onPlaySoundClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main White Card Container
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.White.copy(alpha = 0.9f))
            ) {
                // Core Card Content Layout (Completely clean with no inner elements)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Color Splash Image
                    Image(
                        painter = painterResource(currentItem.colorImage),
                        contentDescription = null,
                        modifier = Modifier.size(240.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Color Name with first letter colored
                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(currentItem.colorCode),
                                fontWeight = FontWeight.ExtraBold
                            )
                        ) {
                            append(currentItem.name.take(1))
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF1565C0), fontWeight = FontWeight.ExtraBold
                            )
                        ) {
                            append(currentItem.name.drop(1))
                        }
                    }
                    Text(text = annotatedString, fontSize = 48.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Navigation and Badge Row (Outside the main white card)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Arrow
                if (currentIndex > 0) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_left),
                        contentDescription = "Previous",
                        modifier = Modifier
                            .size(72.dp)
                            .clickable { onPreviousClick() }
                    )
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
                if (currentIndex < totalItemsCount - 1) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_right),
                        contentDescription = "Next",
                        modifier = Modifier
                            .size(72.dp)
                            .clickable { onNextClick() }
                    )
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
fun ColorsScreenPreview() {
    MaterialTheme {
        // Providing explicit layout state objects so it processes inside the IDE preview system flawlessly
        val mockColor = ColorItem(
            name = "Pink",
            colorImage = Res.drawable.img_color_pink, // Fallback/temporary icon resource if splash assets are missing
            colorCode = 0xFFFF0000,              // Red Color hex representation
            soundRes = "red_sound"
        )

        ColorsContent(
            currentItem = mockColor,
            currentIndex = 1, // Simulates an active middle item so left & right arrow elements load up
            totalItemsCount = 3,
            onBackClick = {},
            onHomeClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onPlaySoundClick = {})
    }
}