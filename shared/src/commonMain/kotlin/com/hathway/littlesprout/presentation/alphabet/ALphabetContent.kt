package com.hathway.littlesprout.presentation.alphabet

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
import com.hathway.littlesprout.domain.model.AlphabetItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.aa
import littlesprout.shared.generated.resources.icon_speaker
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
                Image(
                    painter = painterResource(Res.drawable.icon_speaker),
                    contentDescription = "Play Audio",
                    modifier = Modifier.size(56.dp) // Adjusted to 56.dp to match back button height seamlessly
                        .clickable { /* Audio callback logic */ })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Flashcard Container
            Card(
                modifier = Modifier.weight(1f).fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Display Letter Image
                    Image(
                        painter = painterResource(currentItem.letterImage),
                        contentDescription = "Letter ${currentItem.letter}",
                        modifier = Modifier.height(120.dp).fillMaxWidth(),
                        contentScale = ContentScale.Fit
                    )

                    // Display Object Image
                    Image(
                        painter = painterResource(currentItem.objectImage),
                        contentDescription = currentItem.description,
                        modifier = Modifier.height(180.dp).fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )

                    // Text Description & Audio Trigger Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable { /* Audio callback logic */ }
                            .padding(horizontal = 24.dp, vertical = 12.dp)) {
                        Text(
                            text = currentItem.description,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("🔊", fontSize = 20.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Controls (Previous / Next)
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isPreviousEnabled) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_left),
                        contentDescription = "Previous",
                        modifier = Modifier.size(72.dp) // Adjusted from 64.dp for improved tap targets
                            .clickable { onPreviousClick() })
                } else {
                    Spacer(modifier = Modifier.size(72.dp))
                }

                if (isNextEnabled) {
                    Image(
                        painter = painterResource(Res.drawable.img_sweep_right),
                        contentDescription = "Next",
                        modifier = Modifier.size(72.dp) // Adjusted from 64.dp for improved tap targets
                            .clickable { onNextClick() })
                } else {
                    Spacer(modifier = Modifier.size(72.dp))
                }
            }
        }
    }
}


// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun AlphabetScreenPreview() {
    MaterialTheme {
        // Supplying static mock data so the Layout Engine renders immediately without actual architecture errors
        val mockItem = AlphabetItem(
            letter = "Aa",
            letterImage = Res.drawable.aa,
            objectImage = Res.drawable.img_apple,
            description = "A for Apple",
            audio = "a_apple"
        )

        AlphabetContent(
            currentItem = mockItem,
            isPreviousEnabled = false, // Emulate first card logic
            isNextEnabled = true,
            onBackClick = {},
            onPreviousClick = {},
            onNextClick = {})
    }
}