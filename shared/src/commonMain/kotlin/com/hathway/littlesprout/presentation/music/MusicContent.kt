package com.hathway.littlesprout.presentation.music

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.hathway.littlesprout.domain.model.MusicItem
import com.hathway.littlesprout.domain.model.MusicType
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_drums
import littlesprout.shared.generated.resources.icon_piano
import littlesprout.shared.generated.resources.icon_rhythm
import littlesprout.shared.generated.resources.icon_sing_along
import littlesprout.shared.generated.resources.img_apple
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_ball
import littlesprout.shared.generated.resources.img_cat
import littlesprout.shared.generated.resources.img_duck
import littlesprout.shared.generated.resources.img_music_bg
import littlesprout.shared.generated.resources.music_main_bg
import org.jetbrains.compose.resources.painterResource

// 2. STATELESS CONTENT: Pure layout rendering logic (Safe for instant Previews!)
@Composable
fun MusicContent(
    items: List<MusicItem>, onBackClick: () -> Unit, onItemClick: (MusicType) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image
        Image(
            painter = painterResource(Res.drawable.music_main_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // 2. Content Layer
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })
            }

            Spacer(modifier = Modifier.height(300.dp)) // Aligns with the design background layout spacing

            // Grid for Music Items (2x2)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
            ) {
                items(items) { musicItem ->
                    MusicGridItem(musicItem) {
                        onItemClick(musicItem.type)
                    }
                }
            }
        }
    }
}


// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun MusicScreenPreview() {
    MaterialTheme {
        // Mocking 4 items to perfectly simulate the target 2x2 layout grid
        val mockMusicItems = listOf(
            MusicItem("Sing Along", Res.drawable.icon_sing_along, MusicType.SING_ALONG),
            MusicItem("Piano", Res.drawable.icon_piano, MusicType.PIANO),
            MusicItem("Drums", Res.drawable.icon_drums, MusicType.DRUMS),
            MusicItem("Rhythm", Res.drawable.icon_rhythm, MusicType.RHYTHM)
        )

        MusicContent(items = mockMusicItems, onBackClick = {}, onItemClick = {})
    }
}