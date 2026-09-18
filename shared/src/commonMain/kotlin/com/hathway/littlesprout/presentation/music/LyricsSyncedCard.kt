package com.hathway.littlesprout.presentation.music

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LyricsSyncedCard(lyrics: String, progress: Float, modifier: Modifier = Modifier) {
    val lines = remember(lyrics) { lyrics.split("\n").filter { it.isNotBlank() } }

    if (lines.isEmpty()) {
        Box(modifier = modifier.fillMaxWidth())
        return
    }

    val currentLineIndex = (progress * lines.size).toInt().coerceIn(0, lines.size - 1)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.4f), RoundedCornerShape(32.dp))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val start = when {
                lines.size <= 3 -> 0
                currentLineIndex == 0 -> 0
                currentLineIndex == lines.size - 1 -> lines.size - 3
                else -> currentLineIndex - 1
            }
            val end = (start + 2).coerceAtMost(lines.size - 1)

            for (i in start..end) {
                val isCurrent = i == currentLineIndex
                Text(
                    text = lines[i].trim(),
                    fontSize = if (isCurrent) 28.sp else 20.sp,
                    fontWeight = if (isCurrent) FontWeight.ExtraBold else FontWeight.Bold,
                    color = if (isCurrent) Color(0xFF1B5E20) else Color(0xFF1B5E20).copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}
