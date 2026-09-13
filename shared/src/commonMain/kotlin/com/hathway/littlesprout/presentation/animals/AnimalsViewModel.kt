package com.hathway.littlesprout.presentation.animals

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.AnimalItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class AnimalsViewModel : ViewModel() {
    private val _animals = MutableStateFlow(
        listOf(
            AnimalItem("Lion", Res.drawable.img_lion),
            AnimalItem("Tiger", Res.drawable.img_tiger),
            AnimalItem("Elephant", Res.drawable.img_elephant),
            AnimalItem("Giraffe", Res.drawable.img_giraffe),
            AnimalItem("Monkey", Res.drawable.img_monkey),
            AnimalItem("Panda", Res.drawable.img_panda),
            AnimalItem("Zebra", Res.drawable.img_zebra),
            AnimalItem("Cat", Res.drawable.img_cat)
        )
    )
    val animals = _animals.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    fun nextAnimal() {
        if (_currentIndex.value < _animals.value.size - 1) {
            _currentIndex.value++
        }
    }

    fun previousAnimal() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
        }
    }
}