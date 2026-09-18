package com.hathway.littlesprout.domain.repository

import com.hathway.littlesprout.domain.model.ActivityProgress
import kotlinx.coroutines.flow.Flow

interface ProgressRepository {
    fun getProgress(): Flow<List<ActivityProgress>>
    suspend fun recordActivity(activityId: String, category: String, completed: Boolean)
    suspend fun resetProgress()
}
