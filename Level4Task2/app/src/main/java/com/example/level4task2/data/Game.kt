package com.example.level4task2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val playerMove: String,
    val computerMove: String,
    val result: String,
    val date: Date
)