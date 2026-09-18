package com.hathway.littlesprout.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hathway.littlesprout.domain.model.ActivityProgress
import com.hathway.littlesprout.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreProgressRepository(
    private val dataStore: DataStore<Preferences>
) : ProgressRepository {

    private object Keys {
        val PROGRESS_DATA = stringPreferencesKey("activity_progress")
    }

    override fun getProgress(): Flow<List<ActivityProgress>> = dataStore.data.map { preferences ->
        val json = preferences[Keys.PROGRESS_DATA] ?: "[]"
        try {
            Json.decodeFromString<List<ActivityProgress>>(json)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun recordActivity(activityId: String, category: String, completed: Boolean) {
        dataStore.edit { preferences ->
            val currentJson = preferences[Keys.PROGRESS_DATA] ?: "[]"
            val currentList = try {
                Json.decodeFromString<List<ActivityProgress>>(currentJson).toMutableList()
            } catch (e: Exception) {
                mutableListOf()
            }

            val index = currentList.indexOfFirst { it.activityId == activityId }
            val now = Clock.System.now().toEpochMilliseconds()

            if (index != -1) {
                val existing = currentList[index]
                currentList[index] = existing.copy(
                    playCount = existing.playCount + 1,
                    completionCount = if (completed) existing.completionCount + 1 else existing.completionCount,
                    lastPlayed = now,
                    lastCompleted = if (completed) now else existing.lastCompleted
                )
            } else {
                currentList.add(
                    ActivityProgress(
                        activityId = activityId,
                        category = category,
                        playCount = 1,
                        completionCount = if (completed) 1 else 0,
                        lastPlayed = now,
                        lastCompleted = if (completed) now else 0
                    )
                )
            }

            preferences[Keys.PROGRESS_DATA] = Json.encodeToString(currentList)
        }
    }

    override suspend fun resetProgress() {
        dataStore.edit { preferences ->
            preferences.remove(Keys.PROGRESS_DATA)
        }
    }
}
