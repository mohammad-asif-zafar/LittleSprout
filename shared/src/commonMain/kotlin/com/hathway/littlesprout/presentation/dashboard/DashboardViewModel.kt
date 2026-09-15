package com.hathway.littlesprout.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.DashboardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.*

class DashboardViewModel : ViewModel() {
    private val _items = MutableStateFlow(
        listOf(
            DashboardItem("Animals", Res.drawable.img_animal),
            DashboardItem("Alphabet", Res.drawable.img_alphabet),
            DashboardItem("Numbers", Res.drawable.img_number),
            DashboardItem("Colors", Res.drawable.img_colors),
            DashboardItem("Shapes", Res.drawable.img_shapes),
            DashboardItem("Songs", Res.drawable.img_songs),
            DashboardItem("Birds", Res.drawable.bird_parrot),
            DashboardItem("Fruits", Res.drawable.icon_fruits)
        )
    )
    val items = _items.asStateFlow()
}