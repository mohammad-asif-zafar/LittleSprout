package com.hathway.littlesprout.presentation.numbers

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.img_numbers_bg
import org.jetbrains.compose.resources.painterResource

@Composable
fun NumbersScreen(
    viewModel: NumbersViewModel,
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image with full design
        Image(
            painter = painterResource(Res.drawable.img_numbers_bg),
            contentDescription = "Numbers Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Navigation and Clickable Areas
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            // Top Bar Overlay
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    // Back button area (the image has one, we place a transparent button over it)
                    Spacer(modifier = Modifier.size(48.dp))
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Sound toggle */ }) {
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(240.dp)) // Offset for the bear and title

            // Grid of clickable numbers
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                // Row 1: 1, 2, 3, 4
                Row(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 1 */ })
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 2 */ })
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 3 */ })
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 4 */ })
                }
                
                Spacer(modifier = Modifier.height(10.dp))

                // Row 2: 5, 6, 7, 8
                Row(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 5 */ })
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 6 */ })
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 7 */ })
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 8 */ })
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 3: 9, 10
                Row(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 9 */ })
                    Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { /* 10 */ })
                    Spacer(modifier = Modifier.weight(2f)) // Empty space for alignment
                }
            }
        }
    }
}