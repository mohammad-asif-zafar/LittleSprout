package com.hathway.littlesprout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.littlesprout.di.AppContainer
import com.hathway.littlesprout.domain.model.MusicType
import com.hathway.littlesprout.navigation.BottomNavigationBar
import com.hathway.littlesprout.navigation.NavItem
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
import com.hathway.littlesprout.presentation.common_components.ParentalGate
import com.hathway.littlesprout.presentation.music.MusicScreen
import com.hathway.littlesprout.presentation.music.MusicViewModel
import com.hathway.littlesprout.presentation.music.SongListScreen
import com.hathway.littlesprout.presentation.music.MusicPlayerScreen
import com.hathway.littlesprout.presentation.parents.*
import com.hathway.littlesprout.presentation.util.CategoryConstants
import com.hathway.littlesprout.util.Constants
import com.hathway.littlesprout.util.getAppInfo
import com.hathway.littlesprout.util.getLinkLauncher

@Composable
@Preview
fun App(appContainer: AppContainer? = null) {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
        var selectedNavItem by remember { mutableStateOf(NavItem.Home) }
        var showParentalGate by remember { mutableStateOf(false) }
        var pendingNavItem by remember { mutableStateOf<NavItem?>(null) }

        // Persistent ViewModels
        val numbersViewModel: NumbersViewModel = viewModel {
            NumbersViewModel(appContainer?.progressRepository)
        }
        val musicViewModel: MusicViewModel = viewModel {
            MusicViewModel(appContainer?.progressRepository, appContainer?.settingsRepository)
        }
        val dashboardViewModel: DashboardViewModel = viewModel {
            DashboardViewModel(appContainer?.progressRepository)
        }

        val settingsViewModel: SettingsViewModel? = if (appContainer != null) {
            viewModel {
                SettingsViewModel(
                    appContainer.settingsRepository, appContainer.progressRepository
                )
            }
        } else null

        val progressViewModel: ProgressViewModel? = if (appContainer != null) {
            viewModel { ProgressViewModel(appContainer.progressRepository) }
        } else null

        val showBottomBar = when (currentScreen) {
            is Screen.Main, is Screen.Progress, is Screen.ForParents -> true
            else -> false
        }

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    BottomNavigationBar(
                        selectedItem = selectedNavItem, onItemSelected = { navItem ->
                            if (navItem == NavItem.Home) {
                                selectedNavItem = navItem
                                currentScreen = Screen.Main
                            } else {
                                pendingNavItem = navItem
                                showParentalGate = true
                            }
                        })
                }
            }) { paddingValues ->
            Box(modifier = Modifier.padding(if (showBottomBar) paddingValues else PaddingValues())) {
                if (showParentalGate) {
                    ParentalGate(
                        onDismiss = {
                            showParentalGate = false
                            pendingNavItem = null
                        },
                        onSuccess = {
                            showParentalGate = false
                            pendingNavItem?.let { navItem ->
                                selectedNavItem = navItem
                                currentScreen = when (navItem) {
                                    NavItem.Home -> Screen.Main
                                    NavItem.Progress -> Screen.Progress
                                    NavItem.Parents -> Screen.ForParents
                                }
                            }
                            pendingNavItem = null
                        }
                    )
                }
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
                        DashboardScreen(viewModel = dashboardViewModel, onAnimalsClick = {
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
                        }, onBannerClick = {}, onGoalClick = {}, onLetsGoClick = {})
                    }

                    is Screen.Progress -> {
                        if (progressViewModel != null) {
                            val progressData by progressViewModel.progress.collectAsState()
                            val totalActivities by progressViewModel.totalActivitiesExplored.collectAsState()
                            val songsPlayed by progressViewModel.songsPlayed.collectAsState()

                            ProgressScreen(
                                totalActivities = totalActivities,
                                songsPlayed = songsPlayed,
                                progressData = progressData,
                                onBackClick = {
                                    selectedNavItem = NavItem.Home
                                    currentScreen = Screen.Main
                                })
                        }
                    }

                    is Screen.ForParents -> {
                        val launcher = getLinkLauncher()
                        ForParentsScreen(
                            onBackClick = {
                            selectedNavItem = NavItem.Home
                            currentScreen = Screen.Main
                        },
                            onProgressClick = {
                                selectedNavItem = NavItem.Progress
                                currentScreen = Screen.Progress
                            },
                            onPrivacySafetyClick = { currentScreen = Screen.PrivacySafety },
                            onContentCreditsClick = { currentScreen = Screen.ContentCredits },
                            onContactDeveloperClick = { currentScreen = Screen.ContactDeveloper },
                            onWebsiteClick = { launcher.openUrl(Constants.WEBSITE_URL) },
                            onSettingsClick = { currentScreen = Screen.Settings },
                            onAppInformationClick = { currentScreen = Screen.AppInformation })
                    }

                    is Screen.PrivacySafety -> {
                        PrivacySafetyScreen(
                            onBackClick = { currentScreen = Screen.ForParents },
                            onPrivacyPolicyClick = { currentScreen = Screen.PrivacyPolicy })
                    }

                    is Screen.PrivacyPolicy -> {
                        PrivacyPolicyScreen(onBackClick = { currentScreen = Screen.PrivacySafety })
                    }

                    is Screen.ContentCredits -> {
                        CreditsScreen(onBackClick = { currentScreen = Screen.ForParents })
                    }

                    is Screen.ContactDeveloper -> {
                        val launcher = getLinkLauncher()
                        ContactScreen(
                            onBackClick = { currentScreen = Screen.ForParents },
                            onEmailClick = {
                                launcher.sendEmail(
                                    Constants.SUPPORT_EMAIL, "Little Sprout Support"
                                )
                            },
                            onWebsiteClick = { launcher.openUrl(Constants.WEBSITE_URL) })
                    }

                    is Screen.AppInformation -> {
                        AppInfoScreen(
                            appInfo = getAppInfo(),
                            onBackClick = { currentScreen = Screen.ForParents })
                    }

                    is Screen.Settings -> {
                        if (settingsViewModel != null) {
                            val settings by settingsViewModel.settings.collectAsState()
                            SettingsScreen(
                                settings = settings,
                                onSoundChanged = { settingsViewModel.updateSoundEnabled(it) },
                                onMusicChanged = { settingsViewModel.updateMusicEnabled(it) },
                                onAutoPlayChanged = { settingsViewModel.updateAutoPlayEnabled(it) },
                                onQuietModeChanged = { settingsViewModel.updateQuietModeEnabled(it) },
                                onResetProgress = { settingsViewModel.resetProgress() },
                                onPrivacySafetyClick = { currentScreen = Screen.PrivacySafety },
                                onAboutClick = { currentScreen = Screen.AppInformation },
                                onBackClick = { currentScreen = Screen.ForParents })
                        }
                    }

                    is Screen.Animals -> {
                        val viewModel: AnimalsViewModel = viewModel {
                            AnimalsViewModel(appContainer?.progressRepository)
                        }
                        AnimalsScreen(viewModel = viewModel, onBackClick = {
                            currentScreen = Screen.Main
                        }, onHomeClick = {
                            currentScreen = Screen.Main
                        })
                    }

                    is Screen.Alphabet -> {
                        val viewModel: AlphabetViewModel = viewModel {
                            AlphabetViewModel(
                                appContainer?.progressRepository, appContainer?.settingsRepository
                            )
                        }
                        AlphabetScreen(viewModel = viewModel, onBackClick = {
                            currentScreen = Screen.Main
                        }, onHomeClick = {
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
                        val viewModel: ColorsViewModel = viewModel {
                            ColorsViewModel(
                                appContainer?.progressRepository, appContainer?.settingsRepository
                            )
                        }
                        ColorsScreen(viewModel = viewModel, onBackClick = {
                            currentScreen = Screen.Main
                        }, onHomeClick = {
                            currentScreen = Screen.Main
                        })
                    }

                    is Screen.Shapes -> {
                        val viewModel: ShapesViewModel = viewModel {
                            ShapesViewModel(
                                appContainer?.progressRepository, appContainer?.settingsRepository
                            )
                        }
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
                            })
                    }

                    is Screen.Birds -> {
                        val viewModelBirds: CommonViewModel = viewModel {
                            CommonViewModel(
                                appContainer?.progressRepository, appContainer?.settingsRepository
                            )
                        }
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
                            onHomeClick = { currentScreen = Screen.Main },
                            onPlaySoundClick = { audioFile ->
                                viewModelBirds.toggleAudioPlayback(
                                    audioFile
                                )
                            })
                    }

                    is Screen.Fruits -> {
                        val viewModelFruits: CommonViewModel = viewModel {
                            CommonViewModel(
                                appContainer?.progressRepository, appContainer?.settingsRepository
                            )
                        }
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
                            onHomeClick = { currentScreen = Screen.Main },
                            onPlaySoundClick = { audioFile ->
                                viewModelFruits.toggleAudioPlayback(
                                    audioFile
                                )
                            })
                    }

                    is Screen.Vehicle -> {
                        val viewModelVehicle: CommonViewModel = viewModel {
                            CommonViewModel(
                                appContainer?.progressRepository, appContainer?.settingsRepository
                            )
                        }
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
                            onHomeClick = { currentScreen = Screen.Main },
                            onPlaySoundClick = { audioFile ->
                                viewModelVehicle.toggleAudioPlayback(
                                    audioFile
                                )
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
    }
}
