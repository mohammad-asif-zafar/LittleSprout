package com.hathway.littlesprout.presentation.alphabet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import littlesprout.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun AlphabetScreen(
    viewModel: AlphabetViewModel,
    onBackClick: () -> Unit
) {
    val items by viewModel.alphabetList.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val currentItem = items[currentIndex]

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.img_alphabet_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(48.dp).clickable { onBackClick() }
                )
                Text(
                    text = "Alphabets",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                IconButton(onClick = { /* Settings */ }) {
                    Text("⚙️", fontSize = 20.sp, color = Color.White)
                }
            }

            // Main Card
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.White.copy(alpha = 0.9f)) // Slight transparency to show BG
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Speaker Icon
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text("🔊", fontSize = 32.sp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Letter
                    Text(
                        text = currentItem.letter,
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F) // Reddish color like in the image
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Object Image
                    Image(
                        painter = painterResource(currentItem.objectImage),
                        contentDescription = currentItem.description,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(16.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // Description
                    Text(
                        text = currentItem.description,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))

                    // Navigation Arrows at bottom of card
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp, start = 24.dp, end = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Previous Button
                        Image(
                            painter = painterResource(Res.drawable.img_sweep_left),
                            contentDescription = "Previous",
                            modifier = Modifier
                                .size(56.dp)
                                .clickable(enabled = currentIndex > 0) { viewModel.previousItem() },
                            alpha = if (currentIndex > 0) 1f else 0.5f
                        )

                        // Next Button
                        Image(
                            painter = painterResource(Res.drawable.img_sweep_right),
                            contentDescription = "Next",
                            modifier = Modifier
                                .size(56.dp)
                                .clickable(enabled = currentIndex < items.size - 1) { viewModel.nextItem() },
                            alpha = if (currentIndex < items.size - 1) 1f else 0.5f
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}