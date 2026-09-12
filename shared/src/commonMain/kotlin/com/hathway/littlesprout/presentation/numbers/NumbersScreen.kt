package com.hathway.littlesprout.presentation.numbers

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hathway.littlesprout.domain.model.NumberItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.img_numbers_bg
import org.jetbrains.compose.resources.painterResource

@Composable
fun NumbersScreen(
    viewModel: NumbersViewModel,
    onBackClick: () -> Unit,
    onNumberClick: (Int) -> Unit
) {
    val numbers by viewModel.numbers.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.img_numbers_bg),
            contentDescription = "Numbers Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Overlay Content
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            // Top Bar Areas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick, modifier = Modifier.size(48.dp)) {
                    // Transparent Area
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* Sound Toggle */ }, modifier = Modifier.size(48.dp)) {
                    // Transparent Area
                }
            }

            Spacer(modifier = Modifier.height(230.dp)) // Aligns with the design

            // Grid for Number Items
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                itemsIndexed(numbers) { index, numberItem ->
                    NumberGridItem(numberItem) {
                        onNumberClick(index)
                    }
                }
            }
            
            Spacer(modifier = Modifier.navigationBarsPadding().height(16.dp))
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
