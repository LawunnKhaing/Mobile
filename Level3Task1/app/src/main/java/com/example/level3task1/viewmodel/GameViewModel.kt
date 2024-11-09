package com.example.level3task1.viewmodel

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {


    private val _randomlyChosenGame = mutableStateOf(String())


    private var _gameRatingAccordingToUser = mutableFloatStateOf(5f)

    fun randomAssessableGame() {
        saveRandomlyChosenGame(
            listOf
                (
                "Red Dead Redemption 2",
                "Rocket League",
                "Shadow of the Tombraider",
            ).random()
        )
    }

    private fun saveRandomlyChosenGame(game: String) {
        _randomlyChosenGame.value = game
    }

    fun readRandomlyChosenGame(): String {
        return _randomlyChosenGame.value
    }

    fun readGameRatingAccordingToUser(): Float {
        return _gameRatingAccordingToUser.floatValue
    }

    fun saveGameRatingAccordingToUser(rating: Float) {
        _gameRatingAccordingToUser.floatValue = rating
    }

    fun reset() {
        _randomlyChosenGame.value = String()
        saveGameRatingAccordingToUser(5F)
    }
}