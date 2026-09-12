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
import com.hathway.littlesprout.presentation.dashboard.DashboardScreen
import com.hathway.littlesprout.presentation.dashboard.DashboardViewModel
import com.hathway.littlesprout.presentation.numbers.NumbersScreen
import com.hathway.littlesprout.presentation.numbers.NumbersViewModel
import com.hathway.littlesprout.presentation.onboarding.OnboardingScreen
import com.hathway.littlesprout.presentation.onboarding.OnboardingViewModel
import com.hathway.littlesprout.presentation.splash.SplashScreen
import com.hathway.littlesprout.presentation.splash.SplashViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }

        when (currentScreen) {
            is Screen.Splash -> {
                val viewModel: SplashViewModel = viewModel { SplashViewModel() }
                SplashScreen(
                    viewModel = viewModel,
                    onSplashFinished = {
                        currentScreen = Screen.Onboarding
                    }
                )
            }
            is Screen.Onboarding -> {
                val viewModel: OnboardingViewModel = viewModel { OnboardingViewModel() }
                OnboardingScreen(
                    viewModel = viewModel,
                    onOnboardingFinished = {
                        currentScreen = Screen.Main
                    }
                )
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
                    }
                )
            }
            is Screen.Alphabet -> {
                val viewModel: AlphabetViewModel = viewModel { AlphabetViewModel() }
                AlphabetScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        currentScreen = Screen.Main
                    }
                )
            }
            is Screen.Number -> {
                val viewModel: NumbersViewModel = viewModel { NumbersViewModel() }
                NumbersScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        currentScreen = Screen.Main
                    }
                )
            }
        }
    }
}