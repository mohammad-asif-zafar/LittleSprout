package com.hathway.littlesprout.domain.repository

import com.hathway.littlesprout.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settings: Flow<AppSettings>
    suspend fun updateSettings(settings: AppSettings)
}
