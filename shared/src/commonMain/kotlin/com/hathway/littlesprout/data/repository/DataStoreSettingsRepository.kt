package com.hathway.littlesprout.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.hathway.littlesprout.domain.model.AppSettings
import com.hathway.littlesprout.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreSettingsRepository(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private object Keys {
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val MUSIC_ENABLED = booleanPreferencesKey("music_enabled")
        val AUTO_PLAY_ENABLED = booleanPreferencesKey("auto_play_enabled")
        val QUIET_MODE_ENABLED = booleanPreferencesKey("quiet_mode_enabled")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

    override val settings: Flow<AppSettings> = dataStore.data.map { preferences ->
        AppSettings(
            soundEnabled = preferences[Keys.SOUND_ENABLED] ?: true,
            musicEnabled = preferences[Keys.MUSIC_ENABLED] ?: true,
            autoPlayEnabled = preferences[Keys.AUTO_PLAY_ENABLED] ?: true,
            quietModeEnabled = preferences[Keys.QUIET_MODE_ENABLED] ?: false,
            isOnboardingCompleted = preferences[Keys.ONBOARDING_COMPLETED] ?: false
        )
    }

    override suspend fun updateSettings(settings: AppSettings) {
        dataStore.edit { preferences ->
            preferences[Keys.SOUND_ENABLED] = settings.soundEnabled
            preferences[Keys.MUSIC_ENABLED] = settings.musicEnabled
            preferences[Keys.AUTO_PLAY_ENABLED] = settings.autoPlayEnabled
            preferences[Keys.QUIET_MODE_ENABLED] = settings.quietModeEnabled
            preferences[Keys.ONBOARDING_COMPLETED] = settings.isOnboardingCompleted
        }
    }
}
