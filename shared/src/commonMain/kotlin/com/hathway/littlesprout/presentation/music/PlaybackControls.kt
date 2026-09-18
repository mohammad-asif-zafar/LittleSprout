package com.hathway.littlesprout.presentation.music

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.icon_pause
import littlesprout.shared.generated.resources.icon_reween
import littlesprout.shared.generated.resources.icon_skip
import littlesprout.shared.generated.resources.playgreen

@Composable
fun PlaybackControls(
    isPlaying: Boolean,
    onRewind: () -> Unit,
    onPlayPause: () -> Unit,
    onSkip: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ControlIcon(
            res = Res.drawable.icon_reween,
            contentDescription = "Rewind",
            onClick = onRewind,
            size = 80,
            animationType = AnimationType.SLIDE_LEFT
        )

        Spacer(modifier = Modifier.width(20.dp))

        Box(contentAlignment = Alignment.Center) {
            ControlIcon(
                res = if (isPlaying) Res.drawable.icon_pause else Res.drawable.playgreen,
                contentDescription = if (isPlaying) "Pause" else "Play",
                onClick = onPlayPause,
                size = 110,
                animationType = AnimationType.PULSE
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        ControlIcon(
            res = Res.drawable.icon_skip,
            contentDescription = "Skip",
            onClick = onSkip,
            size = 80,
            animationType = AnimationType.SLIDE_RIGHT
        )
    }
}
