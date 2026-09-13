package com.hathway.littlesprout.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.littlesprout.domain.model.DashboardItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.aa
import littlesprout.shared.generated.resources.img_apple
import littlesprout.shared.generated.resources.img_one
import org.jetbrains.compose.resources.painterResource

// 1. STATEFUL WRAPPER: Consumes production stream states from the ViewModel layer safely
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onAlphabetClick: () -> Unit,
    onNumbersClick: () -> Unit,
    onColorsClick: () -> Unit
) {
    val items by viewModel.items.collectAsState()

    DashboardContent(
        items = items,
        onAlphabetClick = onAlphabetClick,
        onNumbersClick = onNumbersClick,
        onColorsClick = onColorsClick,
        onBannerClick = { /* Banner Click Logic */ },
        onGoalClick = { /* Goal Click Logic */ },
        onLetsGoClick = { /* Let's Go Navigation Logic */ })
}

@Composable
fun DashboardGridItem(item: DashboardItem, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clip(RoundedCornerShape(16.dp)).clickable { onClick() }) {
        Image(
            painter = painterResource(item.icon),
            contentDescription = item.title,
            modifier = Modifier.aspectRatio(1f).fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    }
}

// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun DashboardScreenPreviewMain() {
    MaterialTheme {
        // Generating static category entries assuming icon property holds resource references
        val mockItems = listOf(
            DashboardItem(title = "Alphabet", icon = Res.drawable.aa),
            DashboardItem(title = "Numbers", icon = Res.drawable.img_one),
            DashboardItem(title = "Colors", icon = Res.drawable.img_apple)
        )

        DashboardContent(
            items = mockItems,
            onAlphabetClick = {},
            onNumbersClick = {},
            onColorsClick = {},
            onBannerClick = {},
            onGoalClick = {},
            onLetsGoClick = {})
    }
}