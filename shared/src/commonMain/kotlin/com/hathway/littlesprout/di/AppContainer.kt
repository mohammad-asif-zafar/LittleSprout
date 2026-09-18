package com.hathway.littlesprout.di

import com.hathway.littlesprout.data.createDataStore
import com.hathway.littlesprout.data.repository.DataStoreProgressRepository
import com.hathway.littlesprout.data.repository.DataStoreSettingsRepository
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.domain.repository.SettingsRepository

class AppContainer(context: Any? = null) {
    private val dataStore = createDataStore(context)
    
    val settingsRepository: SettingsRepository by lazy {
        DataStoreSettingsRepository(dataStore)
    }
    
    val progressRepository: ProgressRepository by lazy {
        DataStoreProgressRepository(dataStore)
    }
}
