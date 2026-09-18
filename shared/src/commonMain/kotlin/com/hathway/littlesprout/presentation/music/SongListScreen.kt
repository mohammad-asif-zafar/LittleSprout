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
import littlesprout.shared.generated.resources.icon_bunny_hop
import littlesprout.shared.generated.resources.icon_clap_clap
import littlesprout.shared.generated.resources.icon_hello_sun
import littlesprout.shared.generated.resources.icon_home
import littlesprout.shared.generated.resources.icon_little_chick
import littlesprout.shared.generated.resources.icon_zoom_zoom_car
import littlesprout.shared.generated.resources.img_back_button
import littlesprout.shared.generated.resources.img_music_list_bg
import org.jetbrains.compose.resources.painterResource

// 1. STATEFUL WRAPPER: Observes ViewModel states safely for your core app flow
@Composable
fun SongListScreen(
    viewModel: MusicViewModel,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSongClick: (SongItem) -> Unit
) {
    val songs by viewModel.songs.collectAsState()

    SongListContent(
        songs = songs,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onSongClick = onSongClick
    )
}

// 2. STATELESS CONTENT: Pure layout rendering logic (Safe for instant Previews!)
@Composable
fun SongListContent(
    songs: List<SongItem>,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Image(
                    painter = painterResource(Res.drawable.img_back_button),
                    contentDescription = "Back",
                    modifier = Modifier.size(56.dp).clickable { onBackClick() })

                // Home Button
                Image(
                    painter = painterResource(Res.drawable.icon_home),
                    contentDescription = "Home",
                    modifier = Modifier.size(56.dp).clickable { onHomeClick() })
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
        modifier = Modifier.fillMaxWidth().height(140.dp).clickable { onClick() },
        shape = RoundedCornerShape(32.dp),
        color = Color(song.backgroundColorLong),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Song Icon (Circle)
            Image(
                painter = painterResource(song.imageRes),
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
                title = "Clap Clap",
                lyrics = "\uD83C\uDFB5  Clap Clap Song \n\n" + "Clap, clap, clap your hands, \n" + "Clap them high, clap them low! \n" + "Tap, tap, tap your toes, \n" + "Tap them fast, then nice and slow! \n\n" + "Clap, clap — hooray! \n" + "Tap, tap — play! \n" + "Clap and tap, clap and tap, \n" + "Let’s do it again! ",
                imageRes = Res.drawable.icon_clap_clap,
                backgroundColorLong = 0xFFE1F5FE,
                audioPath = "audio_clap_clap.mp3"
            ), SongItem(
                title = "Bunny Hop",
                lyrics = "\uD83D\uDC30  Bunny Hop \n\n" + "Bunny hop, hop, hop, \n" + "Little bunny never stops! \n" + "Hop to the left, \n" + "Hop to the right, \n" + "Hop, hop, hop — \n" + "What a funny sight! ",
                imageRes = Res.drawable.icon_bunny_hop,
                backgroundColorLong = 0xFFE8F5E9,
                audioPath = "audio_bunny_hop.mp3"
            ), SongItem(
                title = "Hello Sun",
                lyrics = "☀\uFE0F  Hello, Sun! \n\n" + "Hello, sun! Hello, sky! \n" + "Wave your hands and say hi-hi! \n" + "Jump up high, touch your toes, \n" + "Wiggle, wiggle — off we go! \n\n" + "Hi-hi! Bye-bye! \n" + "Wave up high! \n" + "Hello, sun, hello, sky, \n" + "See you soon — bye-bye! ",
                imageRes = Res.drawable.icon_hello_sun,
                backgroundColorLong = 0xFFFFF9C4,
                audioPath = "audio_hello_sun.mp3"
            ), SongItem(
                title = "Little Chick",
                lyrics = "\uD83D\uDC25  Little Chick \n\n" + "Little chick goes peep, peep, peep! \n" + "Wakes up from a cozy sleep. \n" + "Waddle left, waddle right, \n" + "Flap your wings with all your might! \n\n" + "Peep-peep-peep! \n" + "Tweet-tweet-tweet! \n" + "Little chick has dancing feet! ",
                imageRes = Res.drawable.icon_little_chick,
                backgroundColorLong = 0xFFFCE4EC,
                audioPath = "audio_little_chick.mp3"
            ), SongItem(
                title = "Zoom Zoom Car",
                lyrics = "\uD83D\uDE97  Zoom Zoom Car \n\n" + "Zoom, zoom, little car, \n" + "Round the room and not too far! \n" + "Beep-beep here, \n" + "Beep-beep there, \n" + "Zoom around with happy care! \n\n" + "Zoom, zoom! Beep, beep! \n" + "Round and round we go! \n" + "Zoom, zoom, little car, \n" + "Fast, then nice and slow! ",
                imageRes = Res.drawable.icon_zoom_zoom_car,
                backgroundColorLong = 0xFFFCE4EC,
                audioPath = "audio_zoom_zoom.mp3"
            )      // Blue card tint
        )

        SongListContent(songs = mockSongs, onBackClick = {}, onHomeClick = {}, onSongClick = {})
    }
}
