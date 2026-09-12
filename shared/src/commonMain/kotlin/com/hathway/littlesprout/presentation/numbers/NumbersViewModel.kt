package com.hathway.littlesprout.presentation.numbers

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.NumberItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NumbersViewModel : ViewModel() {
    private val _numbers = MutableStateFlow(
        listOf(
            NumberItem(1, "One"),
            NumberItem(2, "Two"),
            NumberItem(3, "Three"),
            NumberItem(4, "Four"),
            NumberItem(5, "Five"),
            NumberItem(6, "Six"),
            NumberItem(7, "Seven"),
            NumberItem(8, "Eight"),
            NumberItem(9, "Nine"),
            NumberItem(10, "Ten")
        )
    )
    val numbers = _numbers.asStateFlow()
}