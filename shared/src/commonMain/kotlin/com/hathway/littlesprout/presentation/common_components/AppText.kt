package com.hathway.littlesprout.presentation.common_components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Reusable AppText component for Compose Multiplatform.
 *
 * Solves two critical text presentation challenges across the project:
 * 1. LTR Isolation: Forces `TextDirection.Ltr` so Latin text/numbers never flip on RTL device locales (e.g. Arabic).
 * 2. Auto-Shrink & Truncation: Dynamically steps down font size between initial size and `minFontSize` 
 *    to fit within `maxLines` (default 2 lines), gracefully truncating with `...` if min size is reached.
 */
@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    minFontSize: TextUnit = 12.sp,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    maxLines: Int = 2,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) {
    val initialFontSize = when {
        fontSize != TextUnit.Unspecified -> fontSize
        style.fontSize != TextUnit.Unspecified -> style.fontSize
        else -> 18.sp
    }

    var currentFontSize by remember(text, initialFontSize) { mutableStateOf(initialFontSize) }

    val mergedStyle = style.merge(
        TextStyle(
            color = color,
            fontSize = currentFontSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            lineHeight = lineHeight,
            textDirection = TextDirection.Ltr
        )
    )

    Text(
        text = text,
        modifier = modifier,
        style = mergedStyle,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = { textLayoutResult ->
            onTextLayout?.invoke(textLayoutResult)

            val hasOverflow = textLayoutResult.didOverflowHeight ||
                    textLayoutResult.didOverflowWidth ||
                    textLayoutResult.lineCount > maxLines

            if (hasOverflow && currentFontSize > minFontSize) {
                val nextSize = (currentFontSize.value - 1f).coerceAtLeast(minFontSize.value)
                if (nextSize < currentFontSize.value) {
                    currentFontSize = nextSize.sp
                }
            }
        }
    )
}
