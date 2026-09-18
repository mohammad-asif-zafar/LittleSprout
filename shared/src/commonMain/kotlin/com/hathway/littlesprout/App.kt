package com.hathway.littlesprout

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.littlesprout.domain.model.MusicType
import com.hathway.littlesprout.navigation.Screen
import com.hathway.littlesprout.presentation.alphabet.AlphabetScreen
import com.hathway.littlesprout.presentation.alphabet.AlphabetViewModel
import com.hathway.littlesprout.presentation.colors.ColorsScreen
import com.hathway.littlesprout.presentation.colors.ColorsViewModel
import com.hathway.littlesprout.presentation.dashboard.DashboardScreen
import com.hathway.littlesprout.presentation.dashboard.DashboardViewModel
import com.hathway.littlesprout.presentation.numbers.NumberDetailScreen
import com.hathway.littlesprout.presentation.numbers.NumbersScreen
import com.hathway.littlesprout.presentation.numbers.NumbersViewModel
import com.hathway.littlesprout.presentation.onboarding.OnboardingScreen
import com.hathway.littlesprout.presentation.onboarding.OnboardingViewModel
import com.hathway.littlesprout.presentation.splash.SplashScreen
import com.hathway.littlesprout.presentation.splash.SplashViewModel
import com.hathway.littlesprout.presentation.shapes.ShapesScreen
import com.hathway.littlesprout.presentation.shapes.ShapesViewModel
import com.hathway.littlesprout.presentation.animals.AnimalsScreen
import com.hathway.littlesprout.presentation.animals.AnimalsViewModel
import com.hathway.littlesprout.presentation.common_components.CommonItemScreen
import com.hathway.littlesprout.presentation.common_components.CommonViewModel
import com.hathway.littlesprout.presentation.music.MusicScreen
import com.hathway.littlesprout.presentation.music.MusicViewModel
import com.hathway.littlesprout.presentation.music.SongListScreen
import com.hathway.littlesprout.presentation.music.MusicPlayerScreen
import com.hathway.littlesprout.presentation.util.CategoryConstants

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
        val numbersViewModel: NumbersViewModel = viewModel { NumbersViewModel() }
        val musicViewModel: MusicViewModel = viewModel { MusicViewModel() }

        when (currentScreen) {
            is Screen.Splash -> {
                val viewModel: SplashViewModel = viewModel { SplashViewModel() }
                SplashScreen(
                    viewModel = viewModel, onSplashFinished = {
                        currentScreen = Screen.Onboarding
                    })
            }

            is Screen.Onboarding -> {
                val viewModel: OnboardingViewModel = viewModel { OnboardingViewModel() }
                OnboardingScreen(
                    viewModel = viewModel, onOnboardingFinished = {
                        currentScreen = Screen.Main
                    })
            }

            is Screen.Main -> {
                val viewModel: DashboardViewModel = viewModel { DashboardViewModel() }
                DashboardScreen(viewModel = viewModel, onAnimalsClick = {
                    currentScreen = Screen.Animals
                }, onAlphabetClick = {
                    currentScreen = Screen.Alphabet
                }, onNumbersClick = {
                    currentScreen = Screen.Number
                }, onColorsClick = {
                    currentScreen = Screen.Colors
                }, onShapesClick = {
                    currentScreen = Screen.Shapes
                }, onMusicClick = {
                    currentScreen = Screen.Music
                }, onBirdsClick = {
                    currentScreen = Screen.Birds
                }, onFruitsClick = {
                    currentScreen = Screen.Fruits
                }, onVehicleClick = {
                    currentScreen = Screen.Vehicle
                }, onBannerClick = {
                    /* Handle Banner Click */
                }, onGoalClick = {
                    /* Handle Goal Click */
                }, onLetsGoClick = {
                    /* Handle Let's Go Click */
                })
            }

            is Screen.Animals -> {
                val viewModel: AnimalsViewModel = viewModel { AnimalsViewModel() }
                AnimalsScreen(viewModel = viewModel, onBackClick = {
                    currentScreen = Screen.Main
                }, onHomeClick = {
                    currentScreen = Screen.Main
                })
            }

            is Screen.Alphabet -> {
                val viewModel: AlphabetViewModel = viewModel { AlphabetViewModel() }
                AlphabetScreen(
                    viewModel = viewModel, onBackClick = {
                        currentScreen = Screen.Main
                    })
            }

            is Screen.Number -> {
                NumbersScreen(viewModel = numbersViewModel, onBackClick = {
                    currentScreen = Screen.Main
                }, onNumberClick = { index ->
                    numbersViewModel.selectNumber(index)
                    currentScreen = Screen.NumberDetail
                })
            }

            is Screen.Colors -> {
                val viewModel: ColorsViewModel = viewModel { ColorsViewModel() }
                ColorsScreen(viewModel = viewModel, onBackClick = {
                    currentScreen = Screen.Main
                }, onHomeClick = {
                    currentScreen = Screen.Main
                })
            }

            is Screen.Shapes -> {
                val viewModel: ShapesViewModel = viewModel { ShapesViewModel() }
                ShapesScreen(viewModel = viewModel, onBackClick = {
                    currentScreen = Screen.Main
                }, onHomeClick = {
                    currentScreen = Screen.Main
                })
            }

            is Screen.Music -> {
                MusicScreen(
                    viewModel = musicViewModel,
                    onBackClick = { currentScreen = Screen.Main },
                    onHomeClick = { currentScreen = Screen.Main },
                    onSongClick = { song -> currentScreen = Screen.MusicPlayer(song) },
                    onItemClick = { type ->
                        if (type == MusicType.SING_ALONG) {
                            currentScreen = Screen.SongList
                        }
                    }
                )
            }

            is Screen.Birds -> {
                val viewModelBirds: CommonViewModel = viewModel { CommonViewModel() }
                val items by viewModelBirds.birdList.collectAsState()
                val currentIndex by viewModelBirds.currentBirdIndex.collectAsState()
                val isPlaying by viewModelBirds.isPlaying.collectAsState()

                androidx.compose.runtime.LaunchedEffect(Unit) {
                    viewModelBirds.playInitialAudio(CategoryConstants.BIRDS)
                }

                CommonItemScreen(
                    items = items,
                    currentIndex = currentIndex,
                    isPlaying = isPlaying,
                    onPreviousClick = { viewModelBirds.previousItem(CategoryConstants.BIRDS) },
                    onNextClick = { viewModelBirds.nextItem(CategoryConstants.BIRDS) },
                    onBackClick = { currentScreen = Screen.Main },
                    onPlaySoundClick = { audioFile -> viewModelBirds.toggleAudioPlayback(audioFile) })
            }

            is Screen.Fruits -> {
                val viewModelFruits: CommonViewModel = viewModel { CommonViewModel() }
                val items by viewModelFruits.fruitsList.collectAsState()
                val currentIndex by viewModelFruits.currentFruitIndex.collectAsState()
                val isPlaying by viewModelFruits.isPlaying.collectAsState()

                androidx.compose.runtime.LaunchedEffect(Unit) {
                    viewModelFruits.playInitialAudio(CategoryConstants.FRUITS)
                }

                CommonItemScreen(
                    items = items,
                    currentIndex = currentIndex,
                    isPlaying = isPlaying,
                    onPreviousClick = { viewModelFruits.previousItem(CategoryConstants.FRUITS) },
                    onNextClick = { viewModelFruits.nextItem(CategoryConstants.FRUITS) },
                    onBackClick = { currentScreen = Screen.Main },
                    onPlaySoundClick = { audioFile -> viewModelFruits.toggleAudioPlayback(audioFile) })
            }

            is Screen.Vehicle -> {
                val viewModelVehicle: CommonViewModel = viewModel { CommonViewModel() }
                val items by viewModelVehicle.vehicleList.collectAsState()
                val currentIndex by viewModelVehicle.currentVehicleIndex.collectAsState()
                val isPlaying by viewModelVehicle.isPlaying.collectAsState()

                androidx.compose.runtime.LaunchedEffect(Unit) {
                    viewModelVehicle.playInitialAudio(CategoryConstants.VEHICLE)
                }

                CommonItemScreen(
                    items = items,
                    currentIndex = currentIndex,
                    isPlaying = isPlaying,
                    onPreviousClick = { viewModelVehicle.previousItem(CategoryConstants.VEHICLE) },
                    onNextClick = { viewModelVehicle.nextItem(CategoryConstants.VEHICLE) },
                    onBackClick = { currentScreen = Screen.Main },
                    onPlaySoundClick = { audioFile -> viewModelVehicle.toggleAudioPlayback(audioFile) })
            }

            is Screen.NumberDetail -> {
                NumberDetailScreen(viewModel = numbersViewModel, onBackClick = {
                    currentScreen = Screen.Number
                }, onHomeClick = {
                    numbersViewModel.clearSelection()
                    currentScreen = Screen.Main
                })
            }

            is Screen.SongList -> {
                SongListScreen(viewModel = musicViewModel, onBackClick = {
                    currentScreen = Screen.Music
                }, onSongClick = { song ->
                    currentScreen = Screen.MusicPlayer(song)
                }, onHomeClick = {
                    currentScreen = Screen.Main
                })
            }

            is Screen.MusicPlayer -> {
                val screen = currentScreen as Screen.MusicPlayer
                MusicPlayerScreen(
                    viewModel = musicViewModel, song = screen.song, onBackClick = {
                        currentScreen = Screen.SongList
                    })
            }
        }
    }
}
