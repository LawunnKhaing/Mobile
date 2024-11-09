package com.example.level4task2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM games ORDER BY date DESC")
    suspend fun getAllGames(): List<Game>

    @Query("DELETE FROM games")
    suspend fun clearAllGames()

    @Query("SELECT COUNT(*) FROM games WHERE result = 'win'")
    suspend fun countWins(): Int

    @Query("SELECT COUNT(*) FROM games WHERE result = 'lose'")
    suspend fun countLosses(): Int

    @Query("SELECT COUNT(*) FROM games WHERE result = 'draw'")
    suspend fun countDraws(): Int

    @Delete
    suspend fun deleteGame(game: Game)

}