package com.hathway.littlesprout.presentation.numbers.number_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.littlesprout.domain.model.NumberItem
import littlesprout.shared.generated.resources.* // Required to access preview assets
import org.jetbrains.compose.resources.painterResource

@Composable
fun NumberGridItem(item: NumberItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
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

// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun NumberGridItemPreview() {
    MaterialTheme {
        // Mocking Number 1 ("One") to test individual layout asset presentation
        val mockItem = NumberItem(
            value = 1,
            name = "One",
            gridImage = Res.drawable.img_one,
            detailImage = Res.drawable.img_hand_one,
            fingerText = "One finger"
        )

        Box(
            modifier = Modifier
                .width(120.dp) // Provide a bounding container width to emulate a grid column slot
                .padding(8.dp)
        ) {
            NumberGridItem(
                item = mockItem,
                onClick = {}
            )
        }
    }
}
