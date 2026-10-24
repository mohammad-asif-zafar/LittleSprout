package com.hathway.littlesprout.fakes

import com.hathway.littlesprout.domain.model.ActivityProgress
import com.hathway.littlesprout.domain.model.AppSettings
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.domain.repository.SettingsRepository
import com.hathway.littlesprout.presentation.music.AudioPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeAudioPlayer(val throwsException: Boolean = false) : AudioPlayer {
    val playedFiles = mutableListOf<String>()
    var isStopped = false
    var isPaused = false
    var isReleased = false
    var isPlayingFlag = false
    var completionCallback: (() -> Unit)? = null

    var customDuration: Long = 1000L
    var customPosition: Long = 0L
    var lastSeekPosition: Long? = null

    override fun preload(fileName: String) {
        if (throwsException) throw RuntimeException("Simulated Preload Failure")
    }

    override fun preload(fileNames: List<String>) {
        if (throwsException) throw RuntimeException("Simulated Preload Failure")
    }

    override fun play(fileName: String, interruptCurrent: Boolean) {
        if (throwsException) throw RuntimeException("Simulated Play Failure")
        playedFiles.add(fileName)
        isPlayingFlag = true
    }

    override fun stop() {
        if (throwsException) throw RuntimeException("Simulated Stop Failure")
        isStopped = true
        isPlayingFlag = false
    }

    override fun pause() {
        if (throwsException) throw RuntimeException("Simulated Pause Failure")
        isPaused = true
        isPlayingFlag = false
    }

    override fun resume() {
        if (throwsException) throw RuntimeException("Simulated Resume Failure")
        isPaused = false
        isPlayingFlag = true
    }

    override fun isPlaying(): Boolean = isPlayingFlag

    override fun release() {
        if (throwsException) throw RuntimeException("Simulated Release Failure")
        isReleased = true
    }

    override fun getDuration(): Long = customDuration
    override fun getCurrentPosition(): Long = customPosition
    override fun seekTo(position: Long) {
        lastSeekPosition = position
        customPosition = position
    }

    override fun onPlaybackComplete(callback: () -> Unit) {
        completionCallback = callback
    }

    fun triggerCompletion() {
        completionCallback?.invoke()
    }
}

class FakeSettingsRepository : SettingsRepository {
    private val _settings = MutableStateFlow(AppSettings())
    override val settings: Flow<AppSettings> = _settings.asStateFlow()

    override suspend fun updateSettings(settings: AppSettings) {
        _settings.value = settings
    }
}

class FakeProgressRepository : ProgressRepository {
    private val _progress = MutableStateFlow<List<ActivityProgress>>(emptyList())
    override fun getProgress(): Flow<List<ActivityProgress>> = _progress.asStateFlow()
    fun getProgressFlow() = _progress.asStateFlow()

    override suspend fun recordActivity(activityId: String, category: String, completed: Boolean) {
        _progress.update { list ->
            val existing = list.find { it.activityId == activityId }
            if (existing != null) {
                list.map {
                    if (it.activityId == activityId) {
                        existing.copy(
                            playCount = existing.playCount + 1,
                            completionCount = existing.completionCount + if (completed) 1 else 0
                        )
                    } else it
                }
            } else {
                list + ActivityProgress(
                    activityId = activityId,
                    category = category,
                    playCount = 1,
                    completionCount = if (completed) 1 else 0
                )
            }
        }
    }

    override suspend fun resetProgress() {
        _progress.value = emptyList()
    }

    // Helper for testing
    fun setProgress(progressList: List<ActivityProgress>) {
        _progress.value = progressList
    }
}
