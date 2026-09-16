package com.hathway.littlesprout.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.DashboardItem
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.*

class DashboardViewModel : ViewModel() {
    private val _items = MutableStateFlow(
        listOf(
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
    )
    val items = _items.asStateFlow()
}