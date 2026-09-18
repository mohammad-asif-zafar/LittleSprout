package com.hathway.littlesprout.presentation.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColoredText(
    text: String,
    modifier: Modifier = Modifier,
    singleColor: Color? = null
) {
    // Dynamic text size reduction for longer words to prevent bubble blowouts
    val computedFontSize = when {
        text.length > 8 -> 34.sp
        text.length > 5 -> 44.sp
        else -> 54.sp
    }

    // FIXED: Dynamically switch background based on whether a single color is used
    val bubbleBackgroundColor = if (singleColor != null) {
        Color.White.copy(alpha = 0.95f) // High-contrast crisp white background for custom colors
    } else {
        Color(0xFFFFF9C4).copy(alpha = 0.9f) // Classic light yellow background for rainbow text
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .background(bubbleBackgroundColor)
            .padding(horizontal = 32.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val colors = listOf(
                Color(0xFFE91E63), // Vibrant Pink/Red
                Color(0xFF4CAF50), // Playful Green
                Color(0xFF2196F3), // Bright Blue
                Color(0xFFFF9800)  // Happy Orange
            )

            text.forEachIndexed { index, char ->
                Text(
                    text = char.toString(),
                    fontSize = computedFontSize,
                    fontWeight = FontWeight.ExtraBold,
                    color = singleColor ?: colors[index % colors.size],
                    letterSpacing = 2.sp
                )
            }
        }
    }
}

