package com.hathway.littlesprout.presentation.numbers

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.NumberItem
import com.hathway.littlesprout.presentation.numbers.number_components.NumberGridItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_four
import littlesprout.shared.generated.resources.img_hand_four
import littlesprout.shared.generated.resources.img_hand_one
import littlesprout.shared.generated.resources.img_hand_three
import littlesprout.shared.generated.resources.img_hand_two
import littlesprout.shared.generated.resources.img_numbers_bg
import littlesprout.shared.generated.resources.img_one
import littlesprout.shared.generated.resources.img_three
import littlesprout.shared.generated.resources.img_two
import org.jetbrains.compose.resources.painterResource

// 2. STATELESS CONTENT: Contains structural layout and drawing rules (Preview safe!)
@Composable
fun NumbersContent(
    numbers: List<NumberItem>,
    onBackClick: () -> Unit,
    onNumberClick: (Int) -> Unit,
    onMusicToggleClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.img_numbers_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Interactive UI Layer
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth().height(80.dp).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back Button
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })
                // Home
                Surface(
                    modifier = Modifier.size(56.dp).clickable { onMusicToggleClick() },
                    shape = CircleShape,
                    color = Color(0xFF19A6B3),
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🏠", fontSize = 24.sp, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp)) // Aligns to background art empty spacing

            // Number Grid layout
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                itemsIndexed(numbers) { index, numberItem ->
                    NumberGridItem(numberItem) {
                        onNumberClick(index)
                    }
                }
            }

            // Bottom Safe Area Spacer
            Spacer(modifier = Modifier.navigationBarsPadding().height(80.dp))
        }
    }
}

// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun NumbersScreenPreview() {
    MaterialTheme {
        // Generating static mockup numbers 1-4 to display the Grid interface accurately
        val mockNumbers = listOf(
            NumberItem(1, "One", Res.drawable.img_one, Res.drawable.img_hand_one, "One finger"),
            NumberItem(2, "Two", Res.drawable.img_two, Res.drawable.img_hand_two, "Two fingers"),
            NumberItem(
                3, "Three", Res.drawable.img_three, Res.drawable.img_hand_three, "Three fingers"
            ),
            NumberItem(4, "Four", Res.drawable.img_four, Res.drawable.img_hand_four, "Four fingers")
        )

        NumbersContent(
            numbers = mockNumbers,
            onBackClick = {},
            onNumberClick = {},
            onMusicToggleClick = {})
    }
}