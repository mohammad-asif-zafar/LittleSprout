package com.hathway.littlesprout.presentation.parents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.model.ActivityProgress
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgressViewModel(
    private val progressRepository: ProgressRepository
) : ViewModel() {

    val progress: StateFlow<List<ActivityProgress>> = progressRepository.getProgress()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalActivitiesExplored = progress.map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val songsPlayed = progress.map { list -> 
        list.filter { it.category == CategoryConstants.SONGS }.sumOf { it.playCount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Add more summary stats as needed
}
