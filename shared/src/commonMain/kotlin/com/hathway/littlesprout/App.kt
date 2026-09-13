package com.hathway.littlesprout

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.hathway.littlesprout.presentation.music.MusicScreen
import com.hathway.littlesprout.presentation.music.MusicViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
        val numbersViewModel: NumbersViewModel = viewModel { NumbersViewModel() }

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
                DashboardScreen(
                    viewModel = viewModel, 
                    onAlphabetClick = {
                        currentScreen = Screen.Alphabet
                    }, 
                    onNumbersClick = {
                        currentScreen = Screen.Number
                    }, 
                    onColorsClick = {
                        currentScreen = Screen.Colors
                    }, 
                    onShapesClick = {
                        currentScreen = Screen.Shapes
                    }, 
                    onAnimalsClick = {
                        currentScreen = Screen.Animals
                    },
                    onSongsClick = {
                        currentScreen = Screen.Music
                    }
                )
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

            is Screen.NumberDetail -> {
                NumberDetailScreen(viewModel = numbersViewModel, onBackClick = {
                    currentScreen = Screen.Number
                }, onHomeClick = {
                    numbersViewModel.clearSelection()
                    currentScreen = Screen.Main
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

            is Screen.Animals -> {
                val viewModel: AnimalsViewModel = viewModel { AnimalsViewModel() }
                AnimalsScreen(viewModel = viewModel, onBackClick = {
                    currentScreen = Screen.Main
                }, onHomeClick = {
                    currentScreen = Screen.Main
                })
            }

            is Screen.Music -> {
                val viewModel: MusicViewModel = viewModel { MusicViewModel() }
                MusicScreen(
                    viewModel = viewModel, 
                    onBackClick = {
                        currentScreen = Screen.Main
                    }, 
                    onItemClick = { type ->
                        // Future music detail implementation
                    }
                )
            }
        }
    }
}