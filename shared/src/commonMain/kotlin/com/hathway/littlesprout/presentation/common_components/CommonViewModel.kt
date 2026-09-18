package com.hathway.littlesprout.presentation.common_components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.littlesprout.presentation.music.getAudioPlayer
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import littlesprout.shared.generated.resources.*

class CommonViewModel : ViewModel() {

    private val _birdList = MutableStateFlow(
        listOf(
            CommonItem(
                letter = "1",
                letterImage = Res.drawable.bird_hornbill,
                objectImage = Res.drawable.bird_hornbill,
                description = "Hornbill",
                audio = "audio_hornbill.mp3"
            ), CommonItem(
                letter = "2",
                letterImage = Res.drawable.bird_crow,
                objectImage = Res.drawable.bird_crow,
                description = "Crow",
                audio = "audio_crow.mp3"
            ), CommonItem(
                letter = "3",
                letterImage = Res.drawable.bird_parrot,
                objectImage = Res.drawable.bird_parrot,
                description = "Parrot",
                audio = "audio_parrot.mp3"
            ), CommonItem(
                letter = "4",
                letterImage = Res.drawable.bird_Flamingo,
                objectImage = Res.drawable.bird_Flamingo,
                description = "Flamingo",
                audio = "audio_flamingo.mp3"
            ), CommonItem(
                letter = "5",
                letterImage = Res.drawable.bird_Woodpecker,
                objectImage = Res.drawable.bird_Woodpecker,
                description = "Woodpecker",
                audio = "audio_woodpecker.mp3"
            ), CommonItem(
                letter = "6",
                letterImage = Res.drawable.bird_Kingfisher,
                objectImage = Res.drawable.bird_Kingfisher,
                description = "Kingfisher",
                audio = "audio_kingfisher.mp3"
            ), CommonItem(
                letter = "7",
                letterImage = Res.drawable.bird_Hen,
                objectImage = Res.drawable.bird_Hen,
                description = "Hen",
                audio = "audio_hen.mp3"
            ), CommonItem(
                letter = "8",
                letterImage = Res.drawable.bird_duck,
                objectImage = Res.drawable.bird_duck,
                description = "Duck",
                audio = "audio_duck.mp3"
            ), CommonItem(
                letter = "9",
                letterImage = Res.drawable.bird_Peacock,
                objectImage = Res.drawable.bird_Peacock,
                description = "Peacock",
                audio = "audio_peacock.mp3"
            ), CommonItem(
                letter = "10",
                letterImage = Res.drawable.bird_owl,
                objectImage = Res.drawable.bird_owl,
                description = "Owl",
                audio = "audio_owl.mp3"
            ), CommonItem(
                letter = "11",
                letterImage = Res.drawable.bird_Penguin,
                objectImage = Res.drawable.bird_Penguin,
                description = "Penguin",
                audio = "audio_penguin.mp3"
            ), CommonItem(
                letter = "12",
                letterImage = Res.drawable.bird_Rooster,
                objectImage = Res.drawable.bird_Rooster,
                description = "Rooster",
                audio = "audio_rooster.mp3"
            ), CommonItem(
                letter = "13",
                letterImage = Res.drawable.bird_Seagull,
                objectImage = Res.drawable.bird_Seagull,
                description = "Seagull",
                audio = "audio_seagull.mp3"
            ), CommonItem(
                letter = "14",
                letterImage = Res.drawable.bird_Eagle,
                objectImage = Res.drawable.bird_Eagle,
                description = "Eagle",
                audio = "audio_eagle.mp3"
            ), CommonItem(
                letter = "15",
                letterImage = Res.drawable.bird_Macaw,
                objectImage = Res.drawable.bird_Macaw,
                description = "Macaw",
                audio = "audio_macaw.mp3"
            ), CommonItem(
                letter = "16",
                letterImage = Res.drawable.bird_Ostrich,
                objectImage = Res.drawable.bird_Ostrich,
                description = "Ostrich",
                audio = "audio_ostrich.mp3"
            ), CommonItem(
                letter = "17",
                letterImage = Res.drawable.bird_Sparrow,
                objectImage = Res.drawable.bird_Sparrow,
                description = "Sparrow",
                audio = "audio_sparrow.mp3"
            )
        )
    )

    private val _fruitsList = MutableStateFlow(
        listOf(
            CommonItem(
                letter = "1",
                letterImage = Res.drawable.img_apple,
                objectImage = Res.drawable.img_apple,
                description = "Apple",
                audio = "audio_apple.mp3"
            ), CommonItem(
                letter = "2",
                letterImage = Res.drawable.fruits_Banana,
                objectImage = Res.drawable.fruits_Banana,
                description = "Banana",
                audio = "audio_banana.mp3"
            ), CommonItem(
                letter = "3",
                letterImage = Res.drawable.fruit_Watermelon,
                objectImage = Res.drawable.fruit_Watermelon,
                description = "Watermelon",
                audio = "audio_watermelon.mp3"
            ), CommonItem(
                letter = "4",
                letterImage = Res.drawable.fruit_Pineapple,
                objectImage = Res.drawable.fruit_Pineapple,
                description = "Pineapple",
                audio = "audio_pineapple.mp3"
            ), CommonItem(
                letter = "5",
                letterImage = Res.drawable.fruit_kiwi,
                objectImage = Res.drawable.fruit_kiwi,
                description = "Kiwi",
                audio = "audio_kiwi.mp3"
            ), CommonItem(
                letter = "7",
                letterImage = Res.drawable.fruit_Oranges,
                objectImage = Res.drawable.fruit_Oranges,
                description = "Orange",
                audio = "audio_orange.mp3"
            ), CommonItem(
                letter = "8",
                letterImage = Res.drawable.fruit_Strawberry,
                objectImage = Res.drawable.fruit_Strawberry,
                description = "Strawberry",
                audio = "audio_strawberry.mp3"
            ), CommonItem(
                letter = "9",
                letterImage = Res.drawable.fruit_Dragon,
                objectImage = Res.drawable.fruit_Dragon,
                description = "Dragon Fruit",
                audio = "audio_dragon_fruit.mp3"
            ), CommonItem(
                letter = "10",
                letterImage = Res.drawable.fruit_Limes,
                objectImage = Res.drawable.fruit_Limes,
                description = "Limes",
                audio = "audio_limes.mp3"
            ), CommonItem(
                letter = "11",
                letterImage = Res.drawable.fruit_Cherry,
                objectImage = Res.drawable.fruit_Cherry,
                description = "Cherry",
                audio = "audio_cherry.mp3"
            ), CommonItem(
                letter = "12",
                letterImage = Res.drawable.fruit_Peach,
                objectImage = Res.drawable.fruit_Peach,
                description = "Peach",
                audio = "audio_peach.mp3"
            ), CommonItem(
                letter = "13",
                letterImage = Res.drawable.fruit_Citrus,
                objectImage = Res.drawable.fruit_Citrus,
                description = "Citrus",
                audio = "audio_citrus.mp3"
            ), CommonItem(
                letter = "14",
                letterImage = Res.drawable.fruit_Grapes,
                objectImage = Res.drawable.fruit_Grapes,
                description = "Grapes",
                audio = "audio_grapes.mp3"
            )
        )
    )

    private val _vehiclesList = MutableStateFlow(
        listOf(
            CommonItem(
                letter = "1",
                letterImage = Res.drawable.vehicle_airplane,
                objectImage = Res.drawable.vehicle_airplane,
                description = "Airplane",
                audio = "audio_airplane.mp3"
            ), CommonItem(
                letter = "2",
                letterImage = Res.drawable.vehicle_ambulance,
                objectImage = Res.drawable.vehicle_ambulance,
                description = "Ambulance",
                audio = "audio_ambulance.mp3"
            ), CommonItem(
                letter = "3",
                letterImage = Res.drawable.vehicle_autorickshaw,
                objectImage = Res.drawable.vehicle_autorickshaw,
                description = "Autorickshaw",
                audio = "audio_autorickshaw.mp3"
            ), CommonItem(
                letter = "4",
                letterImage = Res.drawable.vehicle_bicycle,
                objectImage = Res.drawable.vehicle_bicycle,
                description = "Bicycle",
                audio = "audio_bicycle.mp3"
            ), CommonItem(
                letter = "5",
                letterImage = Res.drawable.vehicle_boat,
                objectImage = Res.drawable.vehicle_boat,
                description = "Boat",
                audio = "audio_boat.mp3"
            ), CommonItem(
                letter = "6",
                letterImage = Res.drawable.vehicle_bullock_cart,
                objectImage = Res.drawable.vehicle_bullock_cart,
                description = "Bullock cart",
                audio = "audio_bullock_cart.mp3"
            ), CommonItem(
                letter = "7",
                letterImage = Res.drawable.vehicle_bus,
                objectImage = Res.drawable.vehicle_bus,
                description = "Bus",
                audio = "audio_bus.mp3"
            ), CommonItem(
                letter = "8",
                letterImage = Res.drawable.vehicle_car,
                objectImage = Res.drawable.vehicle_car,
                description = "Car",
                audio = "audio_car.mp3"
            ), CommonItem(
                letter = "9",
                letterImage = Res.drawable.vehicle_fire_Truck,
                objectImage = Res.drawable.vehicle_fire_Truck,
                description = "Fire Truck",
                audio = "audio_fire_truck.mp3"
            ), CommonItem(
                letter = "10",
                letterImage = Res.drawable.vehicle_Helicopter,
                objectImage = Res.drawable.vehicle_Helicopter,
                description = "Helicopter",
                audio = "audio_helicopter.mp3"
            ), CommonItem(
                letter = "11",
                letterImage = Res.drawable.vehicle_jeep,
                objectImage = Res.drawable.vehicle_jeep,
                description = "Jeep",
                audio = "audio_jeep.mp3"
            ), CommonItem(
                letter = "12",
                letterImage = Res.drawable.vehicle_motor_bike,
                objectImage = Res.drawable.vehicle_motor_bike,
                description = "Motor Bike",
                audio = "audio_motor_bike.mp3"
            ), CommonItem(
                letter = "13",
                letterImage = Res.drawable.vehicle_scooter,
                objectImage = Res.drawable.vehicle_scooter,
                description = "Scooter",
                audio = "audio_scooter.mp3"
            ), CommonItem(
                letter = "14",
                letterImage = Res.drawable.vehicle_ship,
                objectImage = Res.drawable.vehicle_ship,
                description = "Ship",
                audio = "audio_ship.mp3"
            ), CommonItem(
                letter = "15",
                letterImage = Res.drawable.vehicle_Tractor,
                objectImage = Res.drawable.vehicle_Tractor,
                description = "Tractor",
                audio = "audio_tractor.mp3"
            ), CommonItem(
                letter = "16",
                letterImage = Res.drawable.vehicle_Train,
                objectImage = Res.drawable.vehicle_Train,
                description = "Train",
                audio = "audio_train.mp3"
            ), CommonItem(
                letter = "17",
                letterImage = Res.drawable.vehicle_Tram,
                objectImage = Res.drawable.vehicle_Tram,
                description = "Tram",
                audio = "audio_tram.mp3"
            ), CommonItem(
                letter = "18",
                letterImage = Res.drawable.vehicle_Truck,
                objectImage = Res.drawable.vehicle_Truck,
                description = "Truck",
                audio = "audio_truck.mp3"
            ), CommonItem(
                letter = "19",
                letterImage = Res.drawable.vehicle_Van,
                objectImage = Res.drawable.vehicle_Van,
                description = "Van",
                audio = "audio_van.mp3"
            ), CommonItem(
                letter = "20",
                letterImage = Res.drawable.vehicle_yacht,
                objectImage = Res.drawable.vehicle_yacht,
                description = "Yacht",
                audio = "audio_yacht.mp3"
            )
        )
    )

    private val audioPlayer = getAudioPlayer()
    private var playbackJob: Job? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    // Separate Data Streams for All 8 items
    val alphabetList = MutableStateFlow<List<CommonItem>>(emptyList()).asStateFlow()
    val numbersList = MutableStateFlow<List<CommonItem>>(emptyList()).asStateFlow()
    val colorsList = MutableStateFlow<List<CommonItem>>(emptyList()).asStateFlow()
    val shapesList = MutableStateFlow<List<CommonItem>>(emptyList()).asStateFlow()
    val animalsList = MutableStateFlow<List<CommonItem>>(emptyList()).asStateFlow()
    val songsList = MutableStateFlow<List<CommonItem>>(emptyList()).asStateFlow()

    // Existing list flows initialized in previous steps
    val birdList = _birdList.asStateFlow()
    val fruitsList = _fruitsList.asStateFlow()
    val vehicleList = _vehiclesList.asStateFlow()

    // Internal Private State Index Counters
    private val _currentBirdIndex = MutableStateFlow(0)
    private val _currentFruitIndex = MutableStateFlow(0)
    private val _currentVehicleIndex = MutableStateFlow(0)

    // Public State Index Counters
    val currentAlphabetIndex = MutableStateFlow(0)
    val currentNumbersIndex = MutableStateFlow(0)
    val currentColorsIndex = MutableStateFlow(0)
    val currentShapesIndex = MutableStateFlow(0)
    val currentAnimalsIndex = MutableStateFlow(0)
    val currentSongsIndex = MutableStateFlow(0)

    // Connected clean state flows
    val currentBirdIndex = _currentBirdIndex.asStateFlow()
    val currentFruitIndex = _currentFruitIndex.asStateFlow()
    val currentVehicleIndex = _currentVehicleIndex.asStateFlow()

    init {
        // Preload sounds for all categories in this ViewModel
        audioPlayer.preload(_birdList.value.mapNotNull { it.audio })
        audioPlayer.preload(_fruitsList.value.mapNotNull { it.audio })
        audioPlayer.preload(_vehiclesList.value.mapNotNull { it.audio })
        
        audioPlayer.onPlaybackComplete {
            _isPlaying.value = false
        }
    }

    fun playInitialAudio(category: String) {
        playCurrentItemAudio(category)
    }

    private fun playCurrentItemAudio(category: String) {
        val audioFile = when (category) {
            CategoryConstants.BIRDS -> birdList.value.getOrNull(_currentBirdIndex.value)?.audio
            CategoryConstants.FRUITS -> fruitsList.value.getOrNull(_currentFruitIndex.value)?.audio
            CategoryConstants.VEHICLE -> vehicleList.value.getOrNull(_currentVehicleIndex.value)?.audio
            // Add other categories if they have audio
            else -> null
        }
        audioFile?.let { 
            _isPlaying.value = true
            audioPlayer.play(it, interruptCurrent = true)
        }
    }

    fun nextItem(category: String) {
        stopAudio()
        when (category) {
            CategoryConstants.ALPHABET -> if (currentAlphabetIndex.value < alphabetList.value.size - 1) currentAlphabetIndex.value++
            CategoryConstants.NUMBERS -> if (currentNumbersIndex.value < numbersList.value.size - 1) currentNumbersIndex.value++
            CategoryConstants.COLORS -> if (currentColorsIndex.value < colorsList.value.size - 1) currentColorsIndex.value++
            CategoryConstants.SHAPES -> if (currentShapesIndex.value < shapesList.value.size - 1) currentShapesIndex.value++
            CategoryConstants.ANIMALS -> if (currentAnimalsIndex.value < animalsList.value.size - 1) currentAnimalsIndex.value++
            CategoryConstants.SONGS -> if (currentSongsIndex.value < songsList.value.size - 1) currentSongsIndex.value++
            CategoryConstants.BIRDS -> if (_currentBirdIndex.value < birdList.value.size - 1) _currentBirdIndex.value++
            CategoryConstants.FRUITS -> if (_currentFruitIndex.value < fruitsList.value.size - 1) _currentFruitIndex.value++
            CategoryConstants.VEHICLE -> if (_currentVehicleIndex.value < vehicleList.value.size - 1) _currentVehicleIndex.value++
        }
        playCurrentItemAudio(category)
    }

    fun previousItem(category: String) {
        stopAudio()
        when (category) {
            CategoryConstants.ALPHABET -> if (currentAlphabetIndex.value > 0) currentAlphabetIndex.value--
            CategoryConstants.NUMBERS -> if (currentNumbersIndex.value > 0) currentNumbersIndex.value--
            CategoryConstants.COLORS -> if (currentColorsIndex.value > 0) currentColorsIndex.value--
            CategoryConstants.SHAPES -> if (currentShapesIndex.value > 0) currentShapesIndex.value--
            CategoryConstants.ANIMALS -> if (currentAnimalsIndex.value > 0) currentAnimalsIndex.value--
            CategoryConstants.SONGS -> if (currentSongsIndex.value > 0) currentSongsIndex.value--
            CategoryConstants.BIRDS -> if (_currentBirdIndex.value > 0) _currentBirdIndex.value--
            CategoryConstants.FRUITS -> if (_currentFruitIndex.value > 0) _currentFruitIndex.value--
            CategoryConstants.VEHICLE -> if (_currentVehicleIndex.value > 0) _currentVehicleIndex.value--
        }
        playCurrentItemAudio(category)
    }

    fun stopAudio() {
        playbackJob?.cancel()
        playbackJob = null
        try {
            audioPlayer.stop()
        } catch (e: Exception) {
            // Suppress platform engine clear exceptions safely during item transitions
        }
        _isPlaying.value = false
    }

    // FIXED: Generic toggle for all categories matching CommonContent string signature
    fun toggleAudioPlayback(audioFile: String) {
        _isPlaying.value = true
        audioPlayer.play(audioFile, interruptCurrent = true)
    }


    override fun onCleared() {
        super.onCleared()
        stopAudio()
    }
}
