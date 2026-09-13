package com.hathway.littlesprout.presentation.numbers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.hathway.littlesprout.domain.model.NumberItem
import littlesprout.shared.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

// 2. STATELESS CONTENT: Pure layout rendering block optimized for Android Studio Previews
@Composable
fun NumberDetailContent(
    currentItem: NumberItem,
    selectedIndex: Int,
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

                Text(
                    text = "Numbers",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )

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

                    // 3D Number Card
                    Image(
                        painter = painterResource(currentItem.gridImage),
                        contentDescription = null,
                        modifier = Modifier.size(140.dp),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = currentItem.name,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1565C0)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Hand/Fingers Icon
                    Image(
                        painter = painterResource(currentItem.detailImage),
                        contentDescription = null,
                        modifier = Modifier.size(180.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Descriptive Text
                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFFF5252), fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(currentItem.name)
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF1565C0), fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(" finger")
                            if (currentItem.value != 1) append("s")
                        }
                    }
                    Text(text = annotatedString, fontSize = 28.sp)

                    Spacer(modifier = Modifier.height(32.dp))

                    // Sound Button
                    Surface(
                        modifier = Modifier.size(88.dp).clickable { onPlaySoundClick() },
                        shape = CircleShape,
                        color = Color(0xFF42A5F5),
                        shadowElevation = 8.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🔊", fontSize = 44.sp, color = Color.White)
                        }
                    }
                }

                // Left/Right Navigation Arrows Overlay
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (selectedIndex > 0) {
                        Image(
                            painter = painterResource(Res.drawable.img_sweep_left),
                            contentDescription = "Previous",
                            modifier = Modifier.size(64.dp).clickable { onPreviousClick() })
                    } else {
                        Spacer(modifier = Modifier.size(64.dp))
                    }

                    if (selectedIndex < totalItemsCount - 1) {
                        Image(
                            painter = painterResource(Res.drawable.img_sweep_right),
                            contentDescription = "Next",
                            modifier = Modifier.size(64.dp).clickable { onNextClick() })
                    } else {
                        Spacer(modifier = Modifier.size(64.dp))
                    }
                }
            }

            // Bottom "Great job!" Badge
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
fun NumberDetailScreenPreview() {
    MaterialTheme {
        // Static layout mockup item mapping Number 2 ("Two") to simulate a middle state index
        val mockNumberItem = NumberItem(
            value = 2,
            name = "Two",
            gridImage = Res.drawable.img_two,
            detailImage = Res.drawable.img_hand_two,
            fingerText = "Two fingers",
            soundRes = "",

            )
        NumberDetailContent(
            currentItem = mockNumberItem,
            selectedIndex = 1, // Activates both previous and next sweep graphic icons
            totalItemsCount = 10,
            onBackClick = {},
            onHomeClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onPlaySoundClick = {})
    }
}