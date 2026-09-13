package com.hathway.littlesprout.presentation.numbers

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.NumberItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun NumbersScreen(
    viewModel: NumbersViewModel,
    onBackClick: () -> Unit,
    onNumberClick: (Int) -> Unit
) {
    val numbers by viewModel.numbers.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image (The full design with bear, sun, etc.)
        Image(
            painter = painterResource(Res.drawable.img_numbers_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // 2. Interactive Layer
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back Button (Using the custom icon)
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() }
                )
                
                // Title Column (If you want crisp text, otherwise background has it)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Numbers",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )
                    Text(
                        text = "Let's learn numbers!",
                        fontSize = 16.sp,
                        color = Color(0xFF1565C0)
                    )
                }

                // Music Button (Red circle with note - adding missing element)
                Surface(
                    modifier = Modifier.size(56.dp).clickable { /* Toggle Background Music */ },
                    shape = CircleShape,
                    color = Color(0xFFFF5252),
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🎵", fontSize = 24.sp, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(180.dp)) // Offset to align with the design empty space

            // Number Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                itemsIndexed(numbers) { index, numberItem ->
                    NumberGridItem(numberItem) {
                        onNumberClick(index)
                    }
                }
            }
            
            // Bottom space for the sign board
            Spacer(modifier = Modifier.navigationBarsPadding().height(80.dp))
        }
    }
}

@Composable
fun NumberGridItem(item: NumberItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(item.gridImage),
            contentDescription = item.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}
