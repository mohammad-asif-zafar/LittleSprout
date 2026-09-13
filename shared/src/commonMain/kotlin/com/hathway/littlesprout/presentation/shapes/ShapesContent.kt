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
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_shape_bg
import littlesprout.shared.generated.resources.img_sweep_left
import littlesprout.shared.generated.resources.img_sweep_right
import littlesprout.shared.generated.resources.shape_circle
import org.jetbrains.compose.resources.painterResource

// 2. STATELESS CONTENT: Pure drawing logic layer optimized for instant IDE Previews
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 35.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })


                // Home Button
                Surface(
                    modifier = Modifier.size(56.dp).clickable { onHomeClick() },
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🏠", fontSize = 24.sp, color = Color(0xFF1565C0))
                    }
                }
            }

            // Main White Card
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp).clip(RoundedCornerShape(40.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

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
                        color = Color(0xFFFFF9C4), shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = currentItem.name,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFD32F2F),
                            modifier = Modifier.padding(horizontal = 40.dp, vertical = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Sound Button
                    Surface(
                        modifier = Modifier.size(88.dp).clickable { onPlaySoundClick() },
                        shape = CircleShape,
                        color = Color(0xFF4CAF50),
                        shadowElevation = 8.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🔊", fontSize = 44.sp, color = Color.White)
                        }
                    }
                }

                // Left/Right Navigation Arrows
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currentIndex > 0) {
                        Image(
                            painter = painterResource(Res.drawable.img_sweep_left),
                            contentDescription = "Previous",
                            modifier = Modifier.size(64.dp).clickable { onPreviousClick() })
                    } else {
                        Spacer(modifier = Modifier.size(64.dp))
                    }

                    if (currentIndex < totalItemsCount - 1) {
                        Image(
                            painter = painterResource(Res.drawable.img_sweep_right),
                            contentDescription = "Next",
                            modifier = Modifier.size(64.dp).clickable { onNextClick() })
                    } else {
                        Spacer(modifier = Modifier.size(64.dp))
                    }
                }
            }

            // Bottom Badge
            Box(
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                contentAlignment = Alignment.Center
            ) {
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
            }
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
