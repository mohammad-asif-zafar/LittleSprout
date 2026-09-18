package com.hathway.littlesprout.navigation

sealed class Screen {
    object Splash : Screen()
    object Onboarding : Screen()
    object Main : Screen()
    object Alphabet : Screen()
    object Number : Screen()
    object NumberDetail : Screen()
    object Colors : Screen()
    object Shapes : Screen()
    object Animals : Screen()
    object Music : Screen()
    object Fruits : Screen()
    object Birds : Screen()
    object Vehicle : Screen()
    object SongList : Screen()
    data class MusicPlayer(val song: com.hathway.littlesprout.domain.model.SongItem) : Screen()

    // New Parent Sections
    object Progress : Screen()
    object ForParents : Screen()
    object PrivacySafety : Screen()
    object PrivacyPolicy : Screen()
    object ContentCredits : Screen()
    object ContactDeveloper : Screen()
    object AppInformation : Screen()
    object Settings : Screen()
}
