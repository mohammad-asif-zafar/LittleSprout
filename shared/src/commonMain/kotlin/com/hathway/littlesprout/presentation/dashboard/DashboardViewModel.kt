package com.hathway.littlesprout.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.model.DashboardItem
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.*

class DashboardViewModel(
    private val progressRepository: ProgressRepository? = null
) : ViewModel() {
    private val allCategories = listOf(
        DashboardItem(CategoryConstants.ANIMALS, Res.drawable.icon_animals),
        DashboardItem(CategoryConstants.ALPHABET, Res.drawable.img_alphabet),
        DashboardItem(CategoryConstants.NUMBERS, Res.drawable.img_number),
        DashboardItem(CategoryConstants.COLORS, Res.drawable.img_colors),
        DashboardItem(CategoryConstants.SHAPES, Res.drawable.img_shapes),
        DashboardItem(CategoryConstants.SONGS, Res.drawable.img_songs),
        DashboardItem(CategoryConstants.BIRDS, Res.drawable.icon_birds),
        DashboardItem(CategoryConstants.FRUITS, Res.drawable.icon_fruits),
        DashboardItem(CategoryConstants.VEHICLE, Res.drawable.icon_vehicle)
    )

    private val _items = MutableStateFlow(allCategories)
    val items = _items.asStateFlow()

    private val _adventureItems = MutableStateFlow<List<DashboardItem>>(emptyList())
    val adventureItems = _adventureItems.asStateFlow()
    
    private val _completedAdventureItems = MutableStateFlow<Set<String>>(emptySet())
    val completedAdventureItems = _completedAdventureItems.asStateFlow()

    init {
        generateNewAdventure()
    }

    fun generateNewAdventure() {
        _adventureItems.value = allCategories.shuffled().take(4)
        _completedAdventureItems.value = emptySet()
    }
    
    fun recordActivityStart(category: String) {
        if (_adventureItems.value.any { it.title == category }) {
            _completedAdventureItems.value = _completedAdventureItems.value + category
        }
        viewModelScope.launch {
            progressRepository?.recordActivity("START_$category", category, false)
        }
    }
}
