package com.hathway.littlesprout.presentation.numbers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import littlesprout.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun NumberDetailScreen(
    viewModel: NumbersViewModel,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    val numbers by viewModel.numbers.collectAsState()
    val selectedIndex by viewModel.selectedNumberIndex.collectAsState()
    
    val currentItem = selectedIndex?.let { numbers[it] } ?: return

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.img_number_item),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

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
                
                Text(
                    text = "Numbers",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )

                // Home Button (Circle in design)
                Surface(
                    modifier = Modifier.size(56.dp).clickable { onHomeClick() },
                    shape = CircleShape,
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🏠", fontSize = 24.sp)
                    }
                }
            }

            // Main Card
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    // Large Number and Text
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(currentItem.gridImage),
                            contentDescription = null,
                            modifier = Modifier.size(120.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = currentItem.name,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1565C0)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Hand Icon
                    Image(
                        painter = painterResource(currentItem.detailImage),
                        contentDescription = null,
                        modifier = Modifier.size(160.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Finger Text
                    val annotatedString = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                            append(currentItem.name)
                        }
                        withStyle(style = SpanStyle(color = Color(0xFF1565C0), fontWeight = FontWeight.Bold)) {
                            append(" finger")
                            if (currentItem.value != 1) append("s")
                        }
                    }
                    Text(text = annotatedString, fontSize = 24.sp)

                    Spacer(modifier = Modifier.height(32.dp))

                    // Sound Button
                    Surface(
                        modifier = Modifier.size(80.dp).clickable { /* Play Sound */ },
                        shape = CircleShape,
                        color = Color(0xFF42A5F5),
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🔊", fontSize = 40.sp, color = Color.White)
                        }
                    }
                }

                // Left Arrow
                if (selectedIndex!! > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.img_sweep_left),
                            contentDescription = "Previous",
                            modifier = Modifier.size(56.dp).clickable { viewModel.previousNumber() }
                        )
                    }
                }

                // Right Arrow
                if (selectedIndex!! < numbers.size - 1) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.img_sweep_right),
                            contentDescription = "Next",
                            modifier = Modifier.size(56.dp).clickable { viewModel.nextNumber() }
                        )
                    }
                }
            }

            // Bottom Bear and "Great job!"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 32.dp, end = 32.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Great job!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E88E5)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("⭐", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}