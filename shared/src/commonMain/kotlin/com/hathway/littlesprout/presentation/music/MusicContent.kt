package com.hathway.littlesprout.presentation.music

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.MusicItem
import com.hathway.littlesprout.domain.model.MusicType
import com.hathway.littlesprout.domain.model.SongItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_clap_clap
import littlesprout.shared.generated.resources.icon_home
import littlesprout.shared.generated.resources.icon_sing_along
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.music_main_bg
import org.jetbrains.compose.resources.painterResource

@Composable
fun MusicContent(
    items: List<MusicItem>,
    songs: List<SongItem>,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSongClick: (SongItem) -> Unit,
    onItemClick: (MusicType) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image
        Image(
            painter = painterResource(Res.drawable.music_main_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })

                Image(
                    painter = painterResource(Res.drawable.icon_home),
                    contentDescription = "Home",
                    modifier = Modifier.size(56.dp).clickable { onHomeClick() })
            }

            if (items.size == 1) {
                val singAlong = items.first()

                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clip(RoundedCornerShape(30.dp))
                            .background(Color.White.copy(alpha = 0.8f))
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(singAlong.image),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = singAlong.name,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1565C0)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(bottom = 32.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(songs.size) { index ->
                            val song = songs[index]
                            SongGridItem(song = song) {
                                onSongClick(song)
                            }
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(300.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items.size) { index ->
                        val musicItem = items[index]
                        MusicGridItem(musicItem) {
                            onItemClick(musicItem.type)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SongGridItem(song: SongItem, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().aspectRatio(0.85f).clickable { onClick() },
        shape = RoundedCornerShape(32.dp),
        color = Color(song.backgroundColorLong).copy(alpha = 0.95f),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(song.imageRes),
                contentDescription = null,
                modifier = Modifier.weight(1f).fillMaxWidth().clip(CircleShape),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = song.title.replace("\n", " "),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20),
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun MusicScreenPreview() {
    MaterialTheme {
        val mockMusicItems = listOf(
            MusicItem("Sing Along", Res.drawable.icon_sing_along, MusicType.SING_ALONG)
        )
        val mockSongs = listOf(
            SongItem("Clap Clap", "", Res.drawable.icon_clap_clap, 0xFFE1F5FE, "")
        )

        MusicContent(
            items = mockMusicItems,
            songs = mockSongs,
            onBackClick = {},
            onHomeClick = {},
            onSongClick = {},
            onItemClick = {})
    }
}
