package com.hathway.littlesprout.presentation.shapes

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.ShapeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class ShapesViewModel : ViewModel() {
    private val _shapes = MutableStateFlow(
        listOf(
            ShapeItem("Circle", Res.drawable.shape_circle),
            ShapeItem("Square", Res.drawable.shape_square),
            ShapeItem("Triangle", Res.drawable.shape_triangle),
            ShapeItem("Rectangle", Res.drawable.shape_rectangle),
            ShapeItem("Oval", Res.drawable.shape_oval),
            ShapeItem("Star", Res.drawable.shape_star),
            ShapeItem("Heart", Res.drawable.shape_heart),
            ShapeItem("Diamond", Res.drawable.shape_diamond),
            ShapeItem("Pentagon", Res.drawable.shape_pentagon),
            ShapeItem("Hexagon", Res.drawable.shape_hexagon),
        )
    )
    val shapes = _shapes.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    fun nextShape() {
        if (_currentIndex.value < _shapes.value.size - 1) {
            _currentIndex.value++
        }
    }

    fun previousShape() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
        }
    }
}