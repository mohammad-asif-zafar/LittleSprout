package com.hathway.littlesprout.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hathway.littlesprout.presentation.util.CategoryConstants

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
    val adventureItems by viewModel.adventureItems.collectAsState()
    val completedItems by viewModel.completedAdventureItems.collectAsState()
    
    var showAdventureDialog by remember { mutableStateOf(false) }
    var showCompletionMessage by remember { mutableStateOf(false) }

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
        onBannerClick = onBannerClick,
        onGoalClick = { showAdventureDialog = true },
        onLetsGoClick = { showAdventureDialog = true })

    if (showAdventureDialog) {
        AdventureDialog(
            items = adventureItems,
            onItemClick = { category ->
                viewModel.recordActivityStart(category)
                if (completedItems.size + 1 >= adventureItems.size) {
                    showAdventureDialog = false
                    showCompletionMessage = true
                } else {
                    navigateToCategory(category, onAnimalsClick, onAlphabetClick, onNumbersClick, onColorsClick, onShapesClick, onMusicClick, onBirdsClick, onFruitsClick, onVehicleClick)
                }
            },
            onDismiss = { showAdventureDialog = false }
        )
    }
    
    if (showCompletionMessage) {
        Dialog(onDismissRequest = { showCompletionMessage = false }) {
            Surface(
                shape = RoundedCornerShape(32.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Great job! 🌟", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("You explored ${adventureItems.size} activities today!", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { 
                        showCompletionMessage = false
                        viewModel.generateNewAdventure()
                    }) {
                        Text("Awesome!")
                    }
                }
            }
        }
    }
}

private fun navigateToCategory(
    category: String,
    onAnimalsClick: () -> Unit,
    onAlphabetClick: () -> Unit,
    onNumbersClick: () -> Unit,
    onColorsClick: () -> Unit,
    onShapesClick: () -> Unit,
    onMusicClick: () -> Unit,
    onBirdsClick: () -> Unit,
    onFruitsClick: () -> Unit,
    onVehicleClick: () -> Unit
) {
    when (category) {
        CategoryConstants.ALPHABET -> onAlphabetClick()
        CategoryConstants.NUMBERS -> onNumbersClick()
        CategoryConstants.COLORS -> onColorsClick()
        CategoryConstants.SHAPES -> onShapesClick()
        CategoryConstants.ANIMALS -> onAnimalsClick()
        CategoryConstants.BIRDS -> onBirdsClick()
        CategoryConstants.SONGS -> onMusicClick()
        CategoryConstants.FRUITS -> onFruitsClick()
        CategoryConstants.VEHICLE -> onVehicleClick()
    }
}
