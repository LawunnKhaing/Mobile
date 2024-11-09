package com.example.level4task1.ui.theme.screens

sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen("home_screen")
    object AddGameScreen: Screen("add_game_screen")
}