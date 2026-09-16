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
                audio = "bird_hornbill"
            ), CommonItem(
                letter = "2",
                letterImage = Res.drawable.bird_crow,
                objectImage = Res.drawable.bird_crow,
                description = "Crow",
                audio = "bird_crow"
            ), CommonItem(
                letter = "3",
                letterImage = Res.drawable.bird_parrot,
                objectImage = Res.drawable.bird_parrot,
                description = "Parrot",
                audio = "Parrot"
            ), CommonItem(
                letter = "4",
                letterImage = Res.drawable.bird_Flamingo,
                objectImage = Res.drawable.bird_Flamingo,
                description = "Flamingo",
                audio = "Flamingo"
            ), CommonItem(
                letter = "5",
                letterImage = Res.drawable.bird_Woodpecker,
                objectImage = Res.drawable.bird_Woodpecker,
                description = "Woodpecker",
                audio = "Woodpecker"
            ), CommonItem(
                letter = "6",
                letterImage = Res.drawable.bird_Kingfisher,
                objectImage = Res.drawable.bird_Kingfisher,
                description = "Kingfisher",
                audio = "Kingfisher"
            ), CommonItem(
                letter = "7",
                letterImage = Res.drawable.bird_Hen,
                objectImage = Res.drawable.bird_Hen,
                description = "Hen",
                audio = "Hen"
            ), CommonItem(
                letter = "8",
                letterImage = Res.drawable.bird_duck,
                objectImage = Res.drawable.bird_duck,
                description = "Duck",
                audio = "Duck"
            ), CommonItem(
                letter = "9",
                letterImage = Res.drawable.bird_Peacock,
                objectImage = Res.drawable.bird_Peacock,
                description = "Peacock",
                audio = "Peacock"
            ), CommonItem(
                letter = "10",
                letterImage = Res.drawable.bird_owl,
                objectImage = Res.drawable.bird_owl,
                description = "Owl",
                audio = "Owl"
            ), CommonItem(
                letter = "11",
                letterImage = Res.drawable.bird_Penguin,
                objectImage = Res.drawable.bird_Penguin,
                description = "Penguin",
                audio = "Penguin"
            ), CommonItem(
                letter = "12",
                letterImage = Res.drawable.bird_Rooster,
                objectImage = Res.drawable.bird_Rooster,
                description = "Rooster",
                audio = "Rooster"
            ), CommonItem(
                letter = "13",
                letterImage = Res.drawable.bird_Seagull,
                objectImage = Res.drawable.bird_Seagull,
                description = "Seagull",
                audio = "Seagull"
            ), CommonItem(
                letter = "14",
                letterImage = Res.drawable.bird_Eagle,
                objectImage = Res.drawable.bird_Eagle,
                description = "Eagle",
                audio = "Eagle"
            ), CommonItem(
                letter = "15",
                letterImage = Res.drawable.bird_Macaw,
                objectImage = Res.drawable.bird_Macaw,
                description = "Macaw",
                audio = "Macaw"
            ), CommonItem(
                letter = "16",
                letterImage = Res.drawable.bird_Ostrich,
                objectImage = Res.drawable.bird_Ostrich,
                description = "Ostrich",
                audio = "Ostrich"
            ), CommonItem(
                letter = "17",
                letterImage = Res.drawable.bird_Sparrow,
                objectImage = Res.drawable.bird_Sparrow,
                description = "Sparrow",
                audio = "Sparrow"
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
                audio = "Apple"
            ), CommonItem(
                letter = "2",
                letterImage = Res.drawable.fruits_Banana,
                objectImage = Res.drawable.fruits_Banana,
                description = "Banana",
                audio = "Banana"
            ), CommonItem(
                letter = "3",
                letterImage = Res.drawable.fruit_Watermelon,
                objectImage = Res.drawable.fruit_Watermelon,
                description = "Watermelon",
                audio = "Watermelon"
            ), CommonItem(
                letter = "4", letterImage = Res.drawable.fruit_Pineapple, // Fallback asset
                objectImage = Res.drawable.fruit_Pineapple, // Fallback asset
                description = "Pineapple", audio = "Pineapple"
            ), CommonItem(
                letter = "5", letterImage = Res.drawable.fruit_kiwi, // Fallback asset
                objectImage = Res.drawable.fruit_kiwi, // Fallback asset
                description = "Kiwi", audio = "Kiwi"
            ), CommonItem(
                letter = "6", letterImage = Res.drawable.bird_parrot, // Fallback asset
                objectImage = Res.drawable.bird_parrot, // Fallback asset
                description = "Orange", audio = "Orange"
            ), CommonItem(
                letter = "7", letterImage = Res.drawable.fruit_Oranges, // Fallback asset
                objectImage = Res.drawable.fruit_Oranges, // Fallback asset
                description = "Orange", audio = "Orange"
            ), CommonItem(
                letter = "8", letterImage = Res.drawable.fruit_Strawberry, // Fallback asset
                objectImage = Res.drawable.fruit_Strawberry, // Fallback asset
                description = "Strawberry", audio = "Strawberry"
            ), CommonItem(
                letter = "9", letterImage = Res.drawable.fruit_Dragon, // Fallback asset
                objectImage = Res.drawable.fruit_Dragon, // Fallback asset
                description = "Dragon", audio = "Dragon"
            ), CommonItem(
                letter = "10", letterImage = Res.drawable.fruit_Limes, // Fallback asset
                objectImage = Res.drawable.fruit_Limes, // Fallback asset
                description = "Limes", audio = "Limes"
            ), CommonItem(
                letter = "11", letterImage = Res.drawable.fruit_Cherry, // Fallback asset
                objectImage = Res.drawable.fruit_Cherry, // Fallback asset
                description = "Cherry", audio = "Cherry"
            ), CommonItem(
                letter = "12", letterImage = Res.drawable.fruit_Peach, // Fallback asset
                objectImage = Res.drawable.fruit_Peach, // Fallback asset
                description = "Peach", audio = "Peach"
            ), CommonItem(
                letter = "13", letterImage = Res.drawable.fruit_Citrus, // Fallback asset
                objectImage = Res.drawable.fruit_Citrus, // Fallback asset
                description = "Citrus", audio = "Citrus"
            ), CommonItem(
                letter = "14", letterImage = Res.drawable.fruit_Grapes, // Fallback asset
                objectImage = Res.drawable.fruit_Grapes, // Fallback asset
                description = "Grapes", audio = "Grapes"
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
                audio = "Airplane"
            ), CommonItem(
                letter = "2",
                letterImage = Res.drawable.vehicle_ambulance,
                objectImage = Res.drawable.vehicle_ambulance,
                description = "Ambulance",
                audio = "Ambulance"
            ), CommonItem(
                letter = "3",
                letterImage = Res.drawable.vehicle_autorickshaw,
                objectImage = Res.drawable.vehicle_autorickshaw,
                description = "Autorickshaw",
                audio = "Autorickshaw"
            ), CommonItem(
                letter = "4",
                letterImage = Res.drawable.vehicle_bicycle,
                objectImage = Res.drawable.vehicle_bicycle,
                description = "Bicycle",
                audio = "Bicycle"
            ), CommonItem(
                letter = "5",
                letterImage = Res.drawable.vehicle_boat,
                objectImage = Res.drawable.vehicle_boat,
                description = "Boat",
                audio = "Boat"
            ), CommonItem(
                letter = "6",
                letterImage = Res.drawable.vehicle_bullock_cart,
                objectImage = Res.drawable.vehicle_bullock_cart,
                description = "Bullock",
                audio = "Bullock"
            ), CommonItem(
                letter = "7",
                letterImage = Res.drawable.vehicle_bus,
                objectImage = Res.drawable.vehicle_bus,
                description = "Bus",
                audio = "Bus"
            ), CommonItem(
                letter = "8",
                letterImage = Res.drawable.vehicle_car,
                objectImage = Res.drawable.vehicle_car,
                description = "Car",
                audio = "Car"
            ), CommonItem(
                letter = "9",
                letterImage = Res.drawable.vehicle_fire_Truck,
                objectImage = Res.drawable.vehicle_fire_Truck,
                description = "Fire Truck",
                audio = "FireTruck"
            ), CommonItem(
                letter = "10",
                letterImage = Res.drawable.vehicle_Helicopter,
                objectImage = Res.drawable.vehicle_Helicopter,
                description = "Helicopter",
                audio = "Helicopter"
            ), CommonItem(
                letter = "11",
                letterImage = Res.drawable.vehicle_jeep,
                objectImage = Res.drawable.vehicle_jeep,
                description = "Jeep",
                audio = "Jeep"
            ), CommonItem(
                letter = "12",
                letterImage = Res.drawable.vehicle_motor_bike,
                objectImage = Res.drawable.vehicle_motor_bike,
                description = "Motor Bike",
                audio = "MotorBike"
            ), CommonItem(
                letter = "13",
                letterImage = Res.drawable.vehicle_scooter,
                objectImage = Res.drawable.vehicle_scooter,
                description = "Scooter",
                audio = "Scooter"
            ), CommonItem(
                letter = "14",
                letterImage = Res.drawable.vehicle_ship,
                objectImage = Res.drawable.vehicle_ship,
                description = "Ship",
                audio = "Ship"
            ), CommonItem(
                letter = "15",
                letterImage = Res.drawable.vehicle_Tractor,
                objectImage = Res.drawable.vehicle_Tractor,
                description = "Tractor",
                audio = "Tractor"
            ), CommonItem(
                letter = "16",
                letterImage = Res.drawable.vehicle_Train,
                objectImage = Res.drawable.vehicle_Train,
                description = "Train",
                audio = "Train"
            ), CommonItem(
                letter = "17",
                letterImage = Res.drawable.vehicle_Tram,
                objectImage = Res.drawable.vehicle_Tram,
                description = "Tram",
                audio = "Tram"
            ), CommonItem(
                letter = "18",
                letterImage = Res.drawable.vehicle_Truck,
                objectImage = Res.drawable.vehicle_Truck,
                description = "Truck",
                audio = "Truck"
            ), CommonItem(
                letter = "19",
                letterImage = Res.drawable.vehicle_Van,
                objectImage = Res.drawable.vehicle_Van,
                description = "Van",
                audio = "Van"
            ), CommonItem(
                letter = "20",
                letterImage = Res.drawable.vehicle_yacht,
                objectImage = Res.drawable.vehicle_yacht,
                description = "Yacht",
                audio = "Yacht"
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
