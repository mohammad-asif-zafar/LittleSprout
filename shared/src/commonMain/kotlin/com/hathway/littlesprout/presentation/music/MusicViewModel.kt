package com.hathway.littlesprout.presentation.music

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.domain.model.MusicItem
import com.hathway.littlesprout.domain.model.MusicType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.*

class MusicViewModel : ViewModel() {
    private val _musicItems = MutableStateFlow(
        listOf(
            MusicItem("Sing Along", Res.drawable.icon_sing_along, MusicType.SING_ALONG),
            MusicItem("Piano", Res.drawable.icon_piano, MusicType.PIANO),
            MusicItem("Drums", Res.drawable.icon_drums, MusicType.DRUMS),
            MusicItem("Rhythm", Res.drawable.icon_rhythm, MusicType.RHYTHM)
        )
    )
    val musicItems = _musicItems.asStateFlow()
}