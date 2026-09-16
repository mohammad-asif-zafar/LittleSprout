package com.hathway.littlesprout.presentation.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
        // 1. Full Screen Background Image (Scenic background)
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
            // 2. Top Bar (Back Button and Speaker)
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button (Yellow circle with white arrow)
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(64.dp) // Slightly scaled down from 80.dp to save top bar space
                        .clickable { onBackClick() })

                // Speaker Icon (Blue circle with white speaker)
                Image(
                    painter = painterResource(Res.drawable.icon_speaker),
                    contentDescription = "Play Audio",
                    modifier = Modifier.size(72.dp) // Slightly scaled down from 90.dp to save top bar space
                        .clickable { /* Audio callback logic */ })
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Main Flashcard Container (REPLACED height(600.dp) WITH weight(1f))
            Box(
                modifier = Modifier.weight(1f) // Automatically fills all remaining center screen real estate
                    .fillMaxWidth(0.98f), contentAlignment = Alignment.Center
            ) {
                // Card Background Image (Thick yellow border card)
                Image(
                    painter = painterResource(Res.drawable.common_card_bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Object Image (Middle - e.g., Parrot)
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(0.95f).padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(currentItem.objectImage),
                            contentDescription = currentItem.description,
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
                            text = currentItem.description.uppercase()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Defined spacing before bottom buttons

            // 4. Bottom Navigation (Previous and Next Buttons)
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
                        modifier = Modifier.size(85.dp) // Optimized down from 100.dp to ensure visibility across devices
                            .clickable { onPreviousClick() })
                } else {
                    Spacer(modifier = Modifier.size(85.dp))
                }

                // Next Button
                if (isNextEnabled) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_right),
                        contentDescription = "Next",
                        modifier = Modifier.size(85.dp) // Optimized down from 100.dp to ensure visibility across devices
                            .clickable { onNextClick() })
                } else {
                    Spacer(modifier = Modifier.size(85.dp))
                }
            }
        }
    }
}


@Composable
fun ColoredText(text: String, modifier: Modifier = Modifier) {
    // Dynamic text size reduction for longer words to prevent bubble blowouts
    val computedFontSize = when {
        text.length > 8 -> 34.sp
        text.length > 5 -> 44.sp
        else -> 54.sp
    }

    // Light yellow bubble background
    Box(
        modifier = modifier.clip(RoundedCornerShape(32.dp))
            .background(Color(0xFFFFF9C4).copy(alpha = 0.9f))
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val colors = listOf(
                Color(0xFFE91E63), // Red
                Color(0xFFFFC107), // Yellow
                Color(0xFF2196F3)  // Blue
            )

            text.forEachIndexed { index, char ->
                Text(
                    text = char.toString(),
                    fontSize = computedFontSize,
                    fontWeight = FontWeight.ExtraBold,
                    color = colors[index % colors.size],
                    letterSpacing = 2.sp
                )
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
            onPreviousClick = {},
            onNextClick = {})
    }
}
