package com.hathway.littlesprout.presentation.shapes

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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.ShapeItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_speaker
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_shape_bg
import littlesprout.shared.generated.resources.img_sweep_left
import littlesprout.shared.generated.resources.img_sweep_right
import littlesprout.shared.generated.resources.shape_circle
import org.jetbrains.compose.resources.painterResource

@Composable
fun ShapesContent(
    currentItem: ShapeItem,
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
            painter = painterResource(Res.drawable.img_shape_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Content Layer
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp), // Normalized vertical padding from 35.dp
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button (Left Corner)
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(56.dp)
                        .clickable { onBackClick() }
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
                // Core Card Content Layout (Clean with no inner float layers)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Shape Image
                    Image(
                        painter = painterResource(currentItem.shapeImage),
                        contentDescription = currentItem.name,
                        modifier = Modifier.size(260.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Shape Name Bubble
                    Surface(
                        color = Color(0xFFFFF9C4),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = currentItem.name,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFD32F2F),
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // MOVED: Bottom Navigation and Badge Row (Outside the main white card)
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
fun ShapesScreenPreview() {
    MaterialTheme {
        // Static layout mockup mapping a basic item to verify middle navigation states
        val mockShape = ShapeItem(
            name = "Circle",
            shapeImage = Res.drawable.shape_circle // Fallback resource string for canvas layout tests
        )

        ShapesContent(
            currentItem = mockShape,
            currentIndex = 1, // Activates both overlay left and right navigation swipe arrows
            totalItemsCount = 3,
            onBackClick = {},
            onHomeClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onPlaySoundClick = {})
    }
}
