package com.hathway.littlesprout.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ActivityProgress(
    val activityId: String,
    val category: String,
    val playCount: Int = 0,
    val completionCount: Int = 0,
    val lastPlayed: Long = 0,
    val lastCompleted: Long = 0
)

data class LearningAreaProgress(
    val category: String,
    val exploredCount: Int,
    val totalCount: Int
)
