package com.hathway.littlesprout.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.DashboardItem
import com.hathway.littlesprout.navigation.BottomNavigationBar
import littlesprout.shared.generated.resources.*
import org.jetbrains.compose.resources.painterResource

// 2. STATELESS CONTENT: Independent layout drawing block (Clean IDE rendering!)
@Composable
fun DashboardContent(
    items: List<DashboardItem>,
    onAlphabetClick: () -> Unit,
    onNumbersClick: () -> Unit,
    onColorsClick: () -> Unit,
    onBannerClick: () -> Unit,
    onGoalClick: () -> Unit,
    onLetsGoClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }, containerColor = Color.Transparent
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image: Sky and Clouds
            Image(
                painter = painterResource(Res.drawable.img_dashboard_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // Dashboard Content Column
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).statusBarsPadding()
            ) {
                // Header: Logo
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Res.drawable.little_sprout),
                        contentDescription = "LittleSprout Logo",
                        modifier = Modifier.height(44.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                // Banner: Hello Little Explorer
                Image(
                    painter = painterResource(Res.drawable.hello_little_sprout),
                    contentDescription = "Welcome Banner",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(20.dp)).clickable { onBannerClick() },
                    contentScale = ContentScale.FillWidth
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Category Grid: 3-column grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(items) { item ->
                        DashboardGridItem(item) {
                            when (item.title) {
                                "Alphabet" -> onAlphabetClick()
                                "Numbers" -> onNumbersClick()
                                "Colors" -> onColorsClick()
                            }
                        }
                    }
                }

                // Today's Goal Section
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { onGoalClick() },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4).copy(alpha = 0.9f)),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(44.dp),
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("⭐", fontSize = 24.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Today's Goal",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF333333)
                            )
                            Text(
                                text = "Play, explore and learn something new!",
                                fontSize = 12.sp,
                                color = Color(0xFF555555)
                            )
                        }
                        Button(
                            onClick = onLetsGoClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            Text("Let's Go!", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        // Generating static category entries assuming icon property holds resource references
        val mockItems = listOf(
            DashboardItem("Animals", Res.drawable.img_animal),
            DashboardItem("Alphabet", Res.drawable.img_alphabet),
            DashboardItem("Numbers", Res.drawable.img_number),
            DashboardItem("Colors", Res.drawable.img_colors),
            DashboardItem("Shapes", Res.drawable.img_shapes),
            DashboardItem("Songs", Res.drawable.img_songs)
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