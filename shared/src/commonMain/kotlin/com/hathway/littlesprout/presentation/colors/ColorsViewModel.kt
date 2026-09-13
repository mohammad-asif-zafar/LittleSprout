package com.hathway.littlesprout.presentation.colors

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.ColorItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class ColorsViewModel : ViewModel() {
    private val _colors = MutableStateFlow(
        listOf(
            ColorItem("Red", Res.drawable.img_color_red, 0xFFFF0000),
            ColorItem("Blue", Res.drawable.img_color_blue, 0xFF0000FF),
            ColorItem("Green", Res.drawable.img_color_green, 0xFF008000),
            ColorItem("Yellow", Res.drawable.img_color_yellow, 0xFFFFFF00),
            ColorItem("Orange", Res.drawable.img_color_orange, 0xFFFFA500),
            ColorItem("Pink", Res.drawable.img_color_pink, 0xFFFFC0CB),
            ColorItem("Brown", Res.drawable.img_color_brown, 0xFFA52A2A),
            ColorItem("Black", Res.drawable.img_color_black, 0xFF000000)
        )
    )
    val colors = _colors.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    fun nextColor() {
        if (_currentIndex.value < _colors.value.size - 1) {
            _currentIndex.value++
        }
    }

    fun previousColor() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
        }
    }
}