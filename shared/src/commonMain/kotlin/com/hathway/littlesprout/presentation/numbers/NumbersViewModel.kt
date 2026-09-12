package com.hathway.littlesprout.presentation.numbers

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.NumberItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class NumbersViewModel : ViewModel() {
    private val _numbers = MutableStateFlow(
        listOf(
            NumberItem(1, "One", Res.drawable.img_one, Res.drawable.img_hand_one, "One finger"),
            NumberItem(2, "Two", Res.drawable.img_two, Res.drawable.img_hand_two, "Two fingers"),
            NumberItem(3, "Three", Res.drawable.img_three, Res.drawable.img_hand_three, "Three fingers"),
            NumberItem(4, "Four", Res.drawable.img_four, Res.drawable.img_hand_four, "Four fingers"),
            NumberItem(5, "Five", Res.drawable.img_five, Res.drawable.img_hand_five, "Five fingers"),
            NumberItem(6, "Six", Res.drawable.img_six, Res.drawable.img_hand_six, "Six fingers"),
            NumberItem(7, "Seven", Res.drawable.img_seven, Res.drawable.img_hand_seven, "Seven fingers"),
            NumberItem(8, "Eight", Res.drawable.img_eight, Res.drawable.img_hand_eight, "Eight fingers"),
            NumberItem(9, "Nine", Res.drawable.img_nine, Res.drawable.img_hand_nine, "Nine fingers"),
            NumberItem(10, "Ten", Res.drawable.img_ten, Res.drawable.img_hand_ten, "Ten fingers")
        )
    )
    val numbers = _numbers.asStateFlow()

    private val _selectedNumberIndex = MutableStateFlow<Int?>(null)
    val selectedNumberIndex = _selectedNumberIndex.asStateFlow()

    fun selectNumber(index: Int) {
        _selectedNumberIndex.value = index
    }

    fun nextNumber() {
        _selectedNumberIndex.value?.let { current ->
            if (current < _numbers.value.size - 1) {
                _selectedNumberIndex.value = current + 1
            }
        }
    }

    fun previousNumber() {
        _selectedNumberIndex.value?.let { current ->
            if (current > 0) {
                _selectedNumberIndex.value = current - 1
            }
        }
    }

    fun clearSelection() {
        _selectedNumberIndex.value = null
    }
}