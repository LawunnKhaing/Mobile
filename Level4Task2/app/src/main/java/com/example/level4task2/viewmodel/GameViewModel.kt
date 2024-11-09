package com.example.level4task2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level4task2.data.Game
import com.example.level4task2.data.GameDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class GameViewModel(val gameDao: GameDao) : ViewModel() {
    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games = _games.asStateFlow()
    var wins = 0
    var draws = 0
    var losses = 0

    fun incrementWins() { wins++ }
    fun incrementDraws() { draws++ }
    fun incrementLosses() { losses++ }

    init {
        getAllGames()
    }

    fun getAllGames() {
        viewModelScope.launch {
            _games.value = gameDao.getAllGames()
        }
    }

    fun deleteGame(game: Game) {
        viewModelScope.launch {
            gameDao.deleteGame(game)
            getAllGames()
        }
    }


    private fun getRandomMove(): String {
        return listOf("Rock", "Paper", "Scissors").random()
    }

    private fun determineResult(playerMove: String, computerMove: String): String {
        return when {
            playerMove == computerMove -> "draw"
            (playerMove == "Rock" && computerMove == "Scissors") ||
                    (playerMove == "Paper" && computerMove == "Rock") ||
                    (playerMove == "Scissors" && computerMove == "Paper") -> "win"

            else -> "lose"
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            gameDao.clearAllGames()
        }
    }

    fun insertGame(game: Game) {
        viewModelScope.launch {
            gameDao.insertGame(game)
            getAllGames()
        }
    }

}


