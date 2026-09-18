package com.hathway.littlesprout.presentation.animals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.domain.model.AnimalItem
import com.hathway.littlesprout.domain.repository.ProgressRepository
import com.hathway.littlesprout.presentation.music.getAudioPlayer
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import littlesprout.shared.generated.resources.*

class AnimalsViewModel(
    private val progressRepository: ProgressRepository? = null
) : ViewModel() {
    private val audioPlayer = getAudioPlayer()

    private val _animals = MutableStateFlow(
        listOf(
            AnimalItem(name = "Lion", image = Res.drawable.img_lion, audio = "audio_lion.mp3"),
            AnimalItem(name = "Tiger", image = Res.drawable.img_tiger, audio = "audio_tiger.mp3"),
            AnimalItem(name = "Elephant", image = Res.drawable.img_elephant, audio = "audio_elephant.mp3"),
            AnimalItem(name = "Giraffe", image = Res.drawable.img_giraffe, audio = "audio_giraffe.mp3"),
            AnimalItem(name = "Monkey", image = Res.drawable.img_monkey, audio = "audio_monkey.mp3"),
            AnimalItem(name = "Panda", image = Res.drawable.img_panda, audio = "audio_panda.mp3"),
            AnimalItem(name = "Zebra", image = Res.drawable.img_zebra, audio = "audio_zebra.mp3"),
            AnimalItem(name = "Cat", image = Res.drawable.img_cat, audio = "audio_cat.mp3")
        )
    )
    val animals = _animals.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    init {
        // Preload animal sounds
        audioPlayer.preload(_animals.value.mapNotNull { it.audio })
        audioPlayer.onPlaybackComplete {
            _isPlaying.value = false
        }
        // Play the first animal sound on launch
        playAnimalSound()
    }

    fun nextAnimal() {
        stopAudio()
        if (_currentIndex.value < _animals.value.size - 1) {
            _currentIndex.value++
            playAnimalSound()
        } else {
            recordProgress("ANIMALS_COMPLETE", true)
        }
    }

    fun previousAnimal() {
        stopAudio()
        if (_currentIndex.value > 0) {
            _currentIndex.value--
            playAnimalSound()
        }
    }

    fun playAnimalSound() {
        val currentAnimal = _animals.value.getOrNull(_currentIndex.value)
        currentAnimal?.audio?.let { audioFile ->
            _isPlaying.value = true
            audioPlayer.play(audioFile, interruptCurrent = true)
            recordProgress(currentAnimal.name, false)
        }
    }

    private fun recordProgress(activityId: String, completed: Boolean) {
        viewModelScope.launch {
            progressRepository?.recordActivity(activityId, CategoryConstants.ANIMALS, completed)
        }
    }

    private fun stopAudio() {
        audioPlayer.stop()
        _isPlaying.value = false
    }

    override fun onCleared() {
        super.onCleared()
        stopAudio()
    }
}
