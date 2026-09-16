package com.hathway.littlesprout.presentation.common_components

import androidx.lifecycle.ViewModel
import com.hathway.littlesprout.presentation.util.CategoryConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import littlesprout.shared.generated.resources.Res
import littlesprout.shared.generated.resources.bird_Eagle
import littlesprout.shared.generated.resources.bird_Flamingo
import littlesprout.shared.generated.resources.bird_Hen
import littlesprout.shared.generated.resources.bird_Kingfisher
import littlesprout.shared.generated.resources.bird_Macaw
import littlesprout.shared.generated.resources.bird_Ostrich
import littlesprout.shared.generated.resources.bird_Peacock
import littlesprout.shared.generated.resources.bird_Penguin
import littlesprout.shared.generated.resources.bird_Rooster
import littlesprout.shared.generated.resources.bird_Seagull
import littlesprout.shared.generated.resources.bird_Sparrow
import littlesprout.shared.generated.resources.bird_Woodpecker
import littlesprout.shared.generated.resources.bird_crow
import littlesprout.shared.generated.resources.bird_duck
import littlesprout.shared.generated.resources.bird_hornbill
import littlesprout.shared.generated.resources.bird_owl
import littlesprout.shared.generated.resources.bird_parrot
import littlesprout.shared.generated.resources.fruit_Cherry
import littlesprout.shared.generated.resources.fruit_Citrus
import littlesprout.shared.generated.resources.fruit_Dragon
import littlesprout.shared.generated.resources.fruit_Grapes
import littlesprout.shared.generated.resources.fruit_Limes
import littlesprout.shared.generated.resources.fruit_Oranges
import littlesprout.shared.generated.resources.fruit_Peach
import littlesprout.shared.generated.resources.fruit_Pineapple
import littlesprout.shared.generated.resources.fruit_Strawberry
import littlesprout.shared.generated.resources.fruit_Watermelon
import littlesprout.shared.generated.resources.fruit_kiwi
import littlesprout.shared.generated.resources.fruits_Banana
import littlesprout.shared.generated.resources.img_apple
import littlesprout.shared.generated.resources.vehicle_Helicopter
import littlesprout.shared.generated.resources.vehicle_Tractor
import littlesprout.shared.generated.resources.vehicle_Train
import littlesprout.shared.generated.resources.vehicle_Tram
import littlesprout.shared.generated.resources.vehicle_Truck
import littlesprout.shared.generated.resources.vehicle_Van
import littlesprout.shared.generated.resources.vehicle_airplane
import littlesprout.shared.generated.resources.vehicle_ambulance
import littlesprout.shared.generated.resources.vehicle_autorickshaw
import littlesprout.shared.generated.resources.vehicle_bicycle
import littlesprout.shared.generated.resources.vehicle_boat
import littlesprout.shared.generated.resources.vehicle_bullock_cart
import littlesprout.shared.generated.resources.vehicle_bus
import littlesprout.shared.generated.resources.vehicle_car
import littlesprout.shared.generated.resources.vehicle_fire_Truck
import littlesprout.shared.generated.resources.vehicle_jeep
import littlesprout.shared.generated.resources.vehicle_motor_bike
import littlesprout.shared.generated.resources.vehicle_scooter
import littlesprout.shared.generated.resources.vehicle_ship
import littlesprout.shared.generated.resources.vehicle_yacht

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
                audio = "bird_crow.mp3"
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
                audio = "audio__watermelon.mp3"
            ), CommonItem(
                letter = "4",
                letterImage = Res.drawable.fruit_Pineapple,
                objectImage = Res.drawable.fruit_Pineapple,
                description = "Pineapple",
                audio = "audio__pineapple.mp3"
            ), CommonItem(
                letter = "5",
                letterImage = Res.drawable.fruit_kiwi,
                objectImage = Res.drawable.fruit_kiwi,
                description = "Kiwi",
                audio = "audio__kiwi.mp3"
            ), CommonItem(
                letter = "6",
                letterImage = Res.drawable.bird_parrot, // Fallback asset
                objectImage = Res.drawable.bird_parrot, // Fallback asset
                description = "Orange",
                audio = "audio_orange.mp3"
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
                description = "Dragon",
                audio = "audio_dragon.mp3"
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
                description = "Bullock",
                audio = "audio_bullock.mp3"
            ), CommonItem(
                letter = "7",
                letterImage = Res.drawable.vehicle_bus,
                objectImage = Res.drawable.vehicle_bus,
                description = "Bus",
                audio = "audio_Bus.mp3"
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
                audio = "audio_firetruck.mp3"
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
                audio = "audio_motorbike.mp3"
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

    fun nextItem(category: String) {
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
    }

    fun previousItem(category: String) {
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
    }

}
