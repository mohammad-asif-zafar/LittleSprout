package com.hathway.littlesprout.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White.copy(alpha = 0.95f), tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Text("🏠", fontSize = 20.sp) },
            label = { Text("Home", fontSize = 11.sp) },
            selected = true,
            onClick = {},
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = Color(0xFF2E7D32),
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFFE8F5E9)
            )
        )
        NavigationBarItem(
            icon = { Text("📊", fontSize = 20.sp) },
            label = { Text("Progress", fontSize = 11.sp) },
            selected = false,
            onClick = {})
        NavigationBarItem(
            icon = { Text("👨‍👩‍👧", fontSize = 20.sp) },
            label = { Text("For Parents", fontSize = 11.sp) },
            selected = false,
            onClick = {})
        NavigationBarItem(
            icon = { Text("👤", fontSize = 20.sp) },
            label = { Text("Profile", fontSize = 11.sp) },
            selected = false,
            onClick = {})
    }
}
