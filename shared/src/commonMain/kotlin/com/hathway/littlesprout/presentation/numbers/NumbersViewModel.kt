package com.hathway.littlesprout.presentation.numbers

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.NumberItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class NumbersViewModel : ViewModel() {
    private val _numbers = MutableStateFlow(
        listOf(
            NumberItem(1, "One", Res.drawable.img_one, Res.drawable.img_hand_one, "One finger",soundRes = "audio_one.mp3"),
            NumberItem(2, "Two", Res.drawable.img_two, Res.drawable.img_hand_two, "Two fingers",soundRes = "audio_two.mp3"),
            NumberItem(3, "Three", Res.drawable.img_three, Res.drawable.img_hand_three, "Three fingers",soundRes = "audio_three.mp3"),
            NumberItem(4, "Four", Res.drawable.img_four, Res.drawable.img_hand_four, "Four fingers",soundRes = "audio_four.mp3"),
            NumberItem(5, "Five", Res.drawable.img_five, Res.drawable.img_hand_five, "Five fingers",soundRes = "audio_five.mp3"),
            NumberItem(6, "Six", Res.drawable.img_six, Res.drawable.img_hand_six, "Six fingers",soundRes = "audio_six.mp3"),
            NumberItem(7, "Seven", Res.drawable.img_seven, Res.drawable.img_hand_seven, "Seven fingers",soundRes = "audio_seven.mp3"),
            NumberItem(8, "Eight", Res.drawable.img_eight, Res.drawable.img_hand_eight, "Eight fingers",soundRes = "audio_eight.mp3"),
            NumberItem(9, "Nine", Res.drawable.img_nine, Res.drawable.img_hand_nine, "Nine fingers",soundRes = "audio_nine.mp3"),
            NumberItem(10, "Ten", Res.drawable.img_ten, Res.drawable.img_hand_ten, "Ten fingers",soundRes = "audio_ten.mp3")
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