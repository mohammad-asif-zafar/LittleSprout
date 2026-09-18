package com.hathway.littlesprout.presentation.music

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ControlIcon(
    res: DrawableResource,
    contentDescription: String,
    onClick: () -> Unit,
    size: Int,
    animationType: AnimationType = AnimationType.NONE
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val targetScale = when {
        isPressed && animationType == AnimationType.PULSE -> 0.75f
        isPressed -> 0.85f
        else -> 1f
    }

    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val offsetX by animateFloatAsState(
        targetValue = if (isPressed && animationType == AnimationType.SLIDE_LEFT) -20f
        else if (isPressed && animationType == AnimationType.SLIDE_RIGHT) 20f
        else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Image(
        painter = painterResource(res),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(size.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offsetX
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                isPressed = true
                onClick()
            }
    )

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(150)
            isPressed = false
        }
    }
}
