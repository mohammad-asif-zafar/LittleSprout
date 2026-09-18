package com.hathway.littlesprout.presentation.dashboard

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.hathway.littlesprout.presentation.util.CategoryConstants
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.hello_little_sprout
import littlesprout.shared.generated.resources.icon_animals
import littlesprout.shared.generated.resources.icon_birds
import littlesprout.shared.generated.resources.icon_fruits
import littlesprout.shared.generated.resources.icon_vehicle
import littlesprout.shared.generated.resources.img_alphabet
import littlesprout.shared.generated.resources.img_colors
import littlesprout.shared.generated.resources.img_dashboard_bg
import littlesprout.shared.generated.resources.img_number
import littlesprout.shared.generated.resources.img_shapes
import littlesprout.shared.generated.resources.img_songs
import littlesprout.shared.generated.resources.little_sprout
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardContent(
    items: List<DashboardItem>,
    onAnimalsClick: () -> Unit,
    onAlphabetClick: () -> Unit,
    onNumbersClick: () -> Unit,
    onColorsClick: () -> Unit,
    onShapesClick: () -> Unit,
    onMusicClick: () -> Unit,
    onFruitsClick: () -> Unit,
    onBirdsClick: () -> Unit,
    onVehicleClick: () -> Unit,
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
            Image(
                painter = painterResource(Res.drawable.img_dashboard_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).statusBarsPadding()
            ) {
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
                Image(
                    painter = painterResource(Res.drawable.hello_little_sprout),
                    contentDescription = "Welcome Banner",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(20.dp)).clickable { onBannerClick() },
                    contentScale = ContentScale.FillWidth
                )

                Spacer(modifier = Modifier.height(20.dp))

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
                                CategoryConstants.ALPHABET -> onAlphabetClick()
                                CategoryConstants.NUMBERS -> onNumbersClick()
                                CategoryConstants.COLORS -> onColorsClick()
                                CategoryConstants.SHAPES -> onShapesClick()
                                CategoryConstants.ANIMALS -> onAnimalsClick()
                                CategoryConstants.BIRDS -> onBirdsClick()
                                CategoryConstants.SONGS -> onMusicClick()
                                CategoryConstants.FRUITS -> onFruitsClick()
                                CategoryConstants.VEHICLE -> onVehicleClick() // Newly registered click listener
                            }
                        }

                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth().padding(
                        horizontal = 16.dp, vertical = 5.dp
                    ).clickable { onGoalClick() },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4).copy(alpha = 0.9f)),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = 12.dp, vertical = 8.dp
                        ), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(35.dp),
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("⭐", fontSize = 18.sp)
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Today's Goal",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF333333)
                            )
                            Text(
                                text = "Play, explore and learn something new!",
                                fontSize = 12.sp,
                                color = Color(0xFF555555),
                                maxLines = 1
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = onLetsGoClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                text = "Let's Go!", fontSize = 12.sp, fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        val mockItems = listOf(
            DashboardItem(CategoryConstants.ANIMALS, Res.drawable.icon_animals),
            DashboardItem(CategoryConstants.ALPHABET, Res.drawable.img_alphabet),
            DashboardItem(CategoryConstants.NUMBERS, Res.drawable.img_number),
            DashboardItem(
                CategoryConstants.COLORS, Res.drawable.img_colors
            ),
            DashboardItem(CategoryConstants.COLORS, Res.drawable.img_colors),
            DashboardItem(CategoryConstants.SHAPES, Res.drawable.img_shapes),
            DashboardItem(CategoryConstants.SONGS, Res.drawable.img_songs),
            DashboardItem(CategoryConstants.BIRDS, Res.drawable.icon_birds),
            DashboardItem(CategoryConstants.FRUITS, Res.drawable.icon_fruits),
            DashboardItem(
                CategoryConstants.VEHICLE, Res.drawable.icon_vehicle
            )
        )

        DashboardContent(
            items = mockItems,
            onAlphabetClick = { },
            onNumbersClick = { },
            onColorsClick = { },
            onShapesClick = { },
            onAnimalsClick = { },
            onBirdsClick = { },
            onMusicClick = { },
            onFruitsClick = { },
            onBannerClick = { },
            onGoalClick = { },
            onVehicleClick = {},
            onLetsGoClick = {})
    }
}