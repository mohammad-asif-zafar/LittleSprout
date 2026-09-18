package com.hathway.littlesprout.presentation.music

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaybackProgress(
    currentTime: String,
    totalTime: String,
    progress: Float,
    onProgressChange: (Float) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isDragged by interactionSource.collectIsDraggedAsState()

    val displayProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = if (isDragged) {
            tween(durationMillis = 0)
        } else {
            tween(durationMillis = 500, easing = LinearEasing)
        },
        label = "SliderProgressAnimation"
    )

    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 48.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currentTime,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
            Text(
                text = totalTime,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
        }

        Slider(
            value = displayProgress,
            onValueChange = onProgressChange,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color(0xFF4CAF50),
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            ),
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    colors = SliderDefaults.colors(thumbColor = Color.White),
                    modifier = Modifier.size(28.dp)
                )
            }
        )
    }
}
