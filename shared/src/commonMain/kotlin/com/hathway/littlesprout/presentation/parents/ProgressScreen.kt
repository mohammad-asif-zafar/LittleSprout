package com.hathway.littlesprout.presentation.parents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.littlesprout.domain.model.ActivityProgress
import com.hathway.littlesprout.presentation.util.CategoryConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    totalActivities: Int,
    songsPlayed: Int,
    progressData: List<ActivityProgress>,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Progress") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("←", fontSize = 24.sp)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text("This Week", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SummaryCard("Activities explored", totalActivities.toString(), Modifier.weight(1f))
                    SummaryCard("Songs played", songsPlayed.toString(), Modifier.weight(1f))
                }
            }
            
            item {
                Text("Learning Areas", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                
                val categories = listOf(
                    CategoryConstants.NUMBERS,
                    CategoryConstants.ALPHABET,
                    CategoryConstants.COLORS,
                    CategoryConstants.SHAPES,
                    CategoryConstants.SONGS,
                    CategoryConstants.ANIMALS,
                    CategoryConstants.BIRDS,
                    CategoryConstants.FRUITS,
                    CategoryConstants.VEHICLE
                )
                
                categories.forEach { category ->
                    val explored = progressData.count { it.category == category && it.playCount > 0 }
                    val total = 10 // This should ideally come from a real count of items per category
                    LearningAreaRow(category, explored, total)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
            Text(value, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}

@Composable
private fun LearningAreaRow(category: String, explored: Int, total: Int) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(category, style = MaterialTheme.typography.bodyMedium)
            Text("$explored explored", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(4.dp))
        val progress = if (total > 0) (explored.toFloat() / total).coerceIn(0f, 1f) else 0f
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}
