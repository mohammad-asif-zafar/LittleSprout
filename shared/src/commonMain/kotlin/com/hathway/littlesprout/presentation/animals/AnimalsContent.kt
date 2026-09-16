package com.hathway.littlesprout.presentation.animals

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
import com.hathway.littlesprout.domain.model.AnimalItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.animal_bg
import littlesprout.shared.generated.resources.icon_speaker
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_cat
import littlesprout.shared.generated.resources.img_lion
import littlesprout.shared.generated.resources.img_sweep_left
import littlesprout.shared.generated.resources.img_sweep_right
import org.jetbrains.compose.resources.painterResource

@Composable
fun AnimalsContent(
    currentItem: AnimalItem,
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
            painter = painterResource(Res.drawable.animal_bg),
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
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() }
                )

                // Actions Layout (Home + Sound)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // MOVED: Sound Button to the Top Right Bar
                    Image(
                        painter = painterResource(Res.drawable.icon_speaker),
                        contentDescription = "Play Audio",
                        modifier = Modifier.size(72.dp) // Slightly scaled down from 90.dp to save top bar space
                            .clickable { /* Audio callback logic */ })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main White Card (Now relies on weight to fill space gracefully)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.White.copy(alpha = 0.9f))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Animal Image
                    Image(
                        painter = painterResource(currentItem.image),
                        contentDescription = currentItem.name,
                        modifier = Modifier.size(350.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Animal Name Bubble
                    Surface(
                        color = Color(0xFFFFF9C4),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = currentItem.name,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFD32F2F),
                            modifier = Modifier.padding(horizontal = 36.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // MOVED: Bottom Navigation and Badge Row outside the main white card
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
                        modifier = Modifier.size(72.dp).clickable { onPreviousClick() }
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
                        modifier = Modifier.size(72.dp).clickable { onNextClick() }
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
fun AnimalsScreenPreview() {
    MaterialTheme {
        // Generating a static item model instance to simulate normal layout presentation state
        val mockAnimal = AnimalItem(
            name = "Cat",
            image = Res.drawable.img_cat // Reuses your existing lion asset reference
        )

        AnimalsContent(
            currentItem = mockAnimal,
            currentIndex = 1, // Mimics item index state where both navigation controls render
            totalItemsCount = 5,
            onBackClick = {},
            onHomeClick = {},
            onPreviousClick = {},
            onNextClick = {},
            onPlaySoundClick = {}
        )
    }
}