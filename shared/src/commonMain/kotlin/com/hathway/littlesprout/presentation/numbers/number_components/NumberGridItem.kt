package com.hathway.littlesprout.presentation.numbers.number_components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.littlesprout.domain.model.NumberItem
import littlesprout.shared.generated.resources.* // Required to access preview assets
import org.jetbrains.compose.resources.painterResource

@Composable
fun NumberGridItem(item: NumberItem, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Bouncy scale feedback on press for toddlers
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "PressScale"
    )

    // Gentle floating wiggle animation
    val infiniteTransition = rememberInfiniteTransition(label = "GridItemWiggle")
    val wiggleScale by infiniteTransition.animateFloat(
        initialValue = 0.97f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "WiggleScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .scale(pressScale * wiggleScale)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Image(
            painter = painterResource(item.gridImage),
            contentDescription = item.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

// 3. THE PREVIEW FUNCTION
@Preview
@Composable
fun NumberGridItemPreview() {
    MaterialTheme {
        // Mocking Number 1 ("One") to test individual layout asset presentation
        val mockItem = NumberItem(
            value = 1,
            name = "One",
            gridImage = Res.drawable.img_one,
            detailImage = Res.drawable.img_hand_one,
            fingerText = "One finger"
        )

        Box(
            modifier = Modifier
                .width(120.dp) // Provide a bounding container width to emulate a grid column slot
                .padding(8.dp)
        ) {
            NumberGridItem(
                item = mockItem,
                onClick = {}
            )
        }
    }
}
