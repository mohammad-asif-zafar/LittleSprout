package com.hathway.littlesprout.presentation.music

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.littlesprout.domain.model.SongItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_music_bus
import littlesprout.shared.generated.resources.img_music_happy
import littlesprout.shared.generated.resources.img_music_list_bg
import littlesprout.shared.generated.resources.img_music_old_macdonald
import littlesprout.shared.generated.resources.img_music_twinkle
import org.jetbrains.compose.resources.painterResource

// 1. STATEFUL WRAPPER: Observes ViewModel states safely for your core app flow
@Composable
fun SongListScreen(
    viewModel: MusicViewModel,
    onBackClick: () -> Unit,
    onSongClick: (SongItem) -> Unit
) {
    val songs by viewModel.songs.collectAsState()

    SongListContent(
        songs = songs,
        onBackClick = onBackClick,
        onSongClick = onSongClick
    )
}

// 2. STATELESS CONTENT: Pure layout rendering logic (Safe for instant Previews!)
@Composable
fun SongListContent(
    songs: List<SongItem>,
    onBackClick: () -> Unit,
    onSongClick: (SongItem) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(Res.drawable.img_music_list_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Content Layer
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() }
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Song List Container
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(songs) { song ->
                    SongListItem(song) {
                        onSongClick(song)
                    }
                }
            }
        }
    }
}

@Composable
fun SongListItem(song: SongItem, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(32.dp),
        color = Color(song.backgroundColor),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Song Icon (Circle)
            Image(
                painter = painterResource(song.icon),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().clip(CircleShape),
                contentScale = ContentScale.FillBounds
            )

        }
    }
}

// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun SongListScreenPreview() {
    MaterialTheme {
        // Mocking list items to preview the vertical lazy list spacing and color rendering
        val mockSongs = listOf(
            SongItem(
                title = "Twinkle Twinkle Little Star",
                icon = Res.drawable.img_music_old_macdonald,
                backgroundColor = 0xFFFFF9C4
            ), // Yellow card tint
            SongItem(
                title = "Old MacDonald Had a Farm",
                icon = Res.drawable.img_music_twinkle,
                backgroundColor = 0xFFE8F5E9
            ),    // Green card tint
            SongItem(
                title = "The Wheels on the Bus",
                icon = Res.drawable.img_music_bus,
                backgroundColor = 0xFFE1F5FE
            ),      // Blue card tint
            SongItem(
                title = "The Wheels on the Bus",
                icon = Res.drawable.img_music_happy,
                backgroundColor = 0xFFE1F5FE
            )       // Blue card tint
        )

        SongListContent(
            songs = mockSongs,
            onBackClick = {},
            onSongClick = {}
        )
    }
}
