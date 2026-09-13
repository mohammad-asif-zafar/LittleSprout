package com.hathway.littlesprout.presentation.music

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hathway.littlesprout.domain.model.MusicItem
import com.hathway.littlesprout.domain.model.MusicType
import org.jetbrains.compose.resources.painterResource

// 1. STATEFUL WRAPPER: Handles your ViewModel reactive flow states safely
@Composable
fun MusicScreen(
    viewModel: MusicViewModel,
    onBackClick: () -> Unit,
    onItemClick: (MusicType) -> Unit
) {
    val items by viewModel.musicItems.collectAsState()

    MusicContent(
        items = items,
        onBackClick = onBackClick,
        onItemClick = onItemClick
    )
}

@Composable
fun MusicGridItem(item: MusicItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(30.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(item.image),
            contentDescription = item.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}
