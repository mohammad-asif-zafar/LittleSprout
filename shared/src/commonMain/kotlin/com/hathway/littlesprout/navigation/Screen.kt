package com.hathway.littlesprout.navigation

sealed class Screen {
    object Splash : Screen()
    object Onboarding : Screen()
    object Main : Screen()
    object Alphabet : Screen()
    object Number : Screen()

}