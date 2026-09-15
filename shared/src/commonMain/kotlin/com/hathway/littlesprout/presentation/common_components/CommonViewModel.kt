package com.hathway.littlesprout.presentation.common_components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.bird_crow
import littlesprout.shared.generated.resources.bird_hornbill
import littlesprout.shared.generated.resources.bird_parrot

class CommonViewModel : ViewModel() {
    private val _birdList = MutableStateFlow(
        listOf(
            CommonItem(
                letter = "Aa",
                letterImage = Res.drawable.bird_parrot,
                objectImage = Res.drawable.bird_parrot,
                description = "Parrot",
                audio = "a_apple"
            ),
            CommonItem(
                letter = "Aa",
                letterImage = Res.drawable.bird_crow,
                objectImage = Res.drawable.bird_crow,
                description = "Parrot",
                audio = "a_apple"
            ),
            CommonItem(
                letter = "Aa",
                letterImage = Res.drawable.bird_hornbill,
                objectImage = Res.drawable.bird_hornbill,
                description = "Parrot",
                audio = "a_apple"
            )
        )
    )
    val birdList = _birdList.asStateFlow()

    private val _currentBirdIIndex = MutableStateFlow(0)
    val currentBirdIIndex = _currentBirdIIndex.asStateFlow()

    fun nextBirdItem() {
        if (_currentBirdIIndex.value < birdList.value.size - 1) {
            _currentBirdIIndex.value++
        }
    }

    fun previousBirdIItem() {
        if (_currentBirdIIndex.value > 0) {
            _currentBirdIIndex.value--
        }
    }
}