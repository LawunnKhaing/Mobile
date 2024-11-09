package com.example.level4task1.Repository

import android.content.Context
import com.example.level4task1.data.Game
import com.example.level4task1.database.GameDao
import com.example.level4task1.database.GameRoomDatabase

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun insert(game: Game) = gameDao.insert(game)

    suspend fun delete(game: Game) = gameDao.delete(game)

    fun getGames() = gameDao.getGames()

    suspend fun deleteAll() = gameDao.deleteAll()

}