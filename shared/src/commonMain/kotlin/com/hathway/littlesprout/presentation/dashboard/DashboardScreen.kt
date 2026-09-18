package com.hathway.littlesprout.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hathway.littlesprout.domain.model.DashboardItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onAnimalsClick: () -> Unit,
    onAlphabetClick: () -> Unit,
    onNumbersClick: () -> Unit,
    onColorsClick: () -> Unit,
    onShapesClick: () -> Unit,
    onMusicClick: () -> Unit,
    onBirdsClick: () -> Unit,
    onFruitsClick: () -> Unit,
    onVehicleClick: () -> Unit,
    onBannerClick : () -> Unit,
    onGoalClick : () -> Unit,
    onLetsGoClick : () -> Unit
) {
    val items by viewModel.items.collectAsState()

    DashboardContent(
        items = items,
        onAlphabetClick = onAlphabetClick,
        onNumbersClick = onNumbersClick,
        onColorsClick = onColorsClick,
        onShapesClick = onShapesClick,
        onAnimalsClick = onAnimalsClick,
        onMusicClick = onMusicClick,
        onFruitsClick = onFruitsClick,
        onBirdsClick = onBirdsClick,
        onVehicleClick = onVehicleClick,
        onBannerClick = { /* Handle Banner Click if needed */ },
        onGoalClick = { /* Handle Goal Click if needed */ },
        onLetsGoClick = { /* Handle Let's Go Click if needed */ })
}

@Composable
fun DashboardGridItem(item: DashboardItem, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }) {
        Image(
            painter = painterResource(item.icon),
            contentDescription = item.title,
            modifier = Modifier.aspectRatio(1f).fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    }
}