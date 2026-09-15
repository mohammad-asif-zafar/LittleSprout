package com.hathway.littlesprout.presentation.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import littlesprout.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun CommonContent(
    currentItem: CommonItem,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean,
    onBackClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.img_alphabet_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar with Back Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(56.dp)
                        .clickable { onBackClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Flashcard Container
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // 1. Speaker Icon (Top Right Corner inside Card)
                    Image(
                        painter = painterResource(Res.drawable.icon_speaker),
                        contentDescription = "Play Audio",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(24.dp)
                            .size(48.dp)
                            .clickable { /* Audio callback logic */ }
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Aligns content in the middle
                        Spacer(modifier = Modifier.weight(1f))

                        // 2. Image (Middle of the Card)
                        Image(
                            painter = painterResource(currentItem.objectImage),
                            contentDescription = currentItem.description,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(250.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.weight(0.5f))

                        // 3. Text (Bottom of the Card)
                        Text(
                            text = currentItem.description,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1B5E20),
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Controls (Previous / Next)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isPreviousEnabled) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_left),
                        contentDescription = "Previous",
                        modifier = Modifier
                            .size(64.dp)
                            .clickable { onPreviousClick() }
                    )
                } else {
                    Spacer(modifier = Modifier.size(64.dp))
                }

                if (isNextEnabled) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_right),
                        contentDescription = "Next",
                        modifier = Modifier
                            .size(64.dp)
                            .clickable { onNextClick() }
                    )
                } else {
                    Spacer(modifier = Modifier.size(64.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun AlphabetScreenPreview() {
    MaterialTheme {
        val mockItem = CommonItem(
            letter = "Aa",
            letterImage = Res.drawable.bird_hornbill,
            objectImage = Res.drawable.img_apple,
            description = "Apple",
            audio = "a_apple"
        )

        CommonContent(
            currentItem = mockItem,
            isPreviousEnabled = true,
            isNextEnabled = true,
            onBackClick = {},
            onPreviousClick = {},
            onNextClick = {}
        )
    }
}
