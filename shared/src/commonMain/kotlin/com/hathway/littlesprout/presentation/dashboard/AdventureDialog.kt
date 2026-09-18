package com.hathway.littlesprout.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hathway.littlesprout.domain.model.DashboardItem
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_birds
import littlesprout.shared.generated.resources.icon_fruits
import littlesprout.shared.generated.resources.icon_vehicle
import littlesprout.shared.generated.resources.img_alphabet
import littlesprout.shared.generated.resources.lets_go_bg
import org.jetbrains.compose.resources.painterResource

@Composable
fun AdventureDialog(
    items: List<DashboardItem>, onItemClick: (String) -> Unit, onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        AdventureDialogContent(
            items = items, onItemClick = onItemClick, onDismiss = onDismiss
        )
    }
}

@Composable
fun AdventureDialogContent(
    items: List<DashboardItem>,
    onItemClick: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Full Screen Background Image: "lets_go_bg" containing the title headers
        Image(
            painter = painterResource(Res.drawable.lets_go_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Precise spacing allocation so content rests below the "Today's Adventure" background wood banner
            Spacer(modifier = Modifier.height(230.dp))

            // EXACT COLOR PALETTE MATRICES MATCHING THE MOCK REFERENCE GRAPHICS
            val itemColors = listOf(
                Color(0xFFFFD54F), // Vibrant Sunbeam Yellow
                Color(0xFF81D4FA), // High-Saturation Sky Blue
                Color(0xFFF06292), // Cozy Pastel Candy Pink
                Color(0xFF81C784)  // Bright Mint Leaf Green
            )

            // Constraints limiting layout bounds safely to a maximum 2x2 presentation sheet
            val chunkedItems = remember(items) { items.take(4).chunked(2) }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                chunkedItems.forEachIndexed { rowIndex, rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEachIndexed { colIndex, item ->
                            val colorIndex = (rowIndex * 2 + colIndex) % itemColors.size
                            AdventureItemCard(
                                item = item,
                                bgColor = itemColors[colorIndex],
                                modifier = Modifier.weight(1f),
                                onClick = { onItemClick(item.title) })
                        }

                        if (rowItems.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3D STYLED TACTILE BUTTON matching the graphic exactly
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .padding(bottom = 8.dp)
                    .border(
                        width = 4.dp,
                        color = Color(0xFF1B5E20), // Deep forest green clay base layer
                        shape = RoundedCornerShape(50.dp)
                    )
            ) {
                Text(
                    text = "Close",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black, // Ultra thick font presentation
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun AdventureItemCard(
    item: DashboardItem, bgColor: Color, modifier: Modifier = Modifier, onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier.aspectRatio(1f).clip(RoundedCornerShape(44.dp)).background(bgColor)
            .border(10.dp, Color.White, RoundedCornerShape(44.dp)).clickable(
                interactionSource = interactionSource,
                indication = null, // Suppresses standard platform ripple overlay blocks
                onClick = onClick
            ).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(item.icon),
            contentDescription = item.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
fun AdventureDialogPreview() {
    val sampleItems = listOf(
        DashboardItem("Alphabet", Res.drawable.img_alphabet),
        DashboardItem("Vehicle", Res.drawable.icon_vehicle),
        DashboardItem("Birds", Res.drawable.icon_birds),
        DashboardItem("Fruits", Res.drawable.icon_fruits)
    )

    MaterialTheme {
        AdventureDialogContent(items = sampleItems, onItemClick = {}, onDismiss = {})
    }
}
