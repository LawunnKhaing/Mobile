package com.example.level4task2.ui.theme.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.level4task2.R

sealed class RPSScreens(val route: String, @StringRes val resourceId: Int, @DrawableRes val painterId: Int) {
    object Play : RPSScreens("play", R.string.play, R.drawable.ic_baseline_gamepad_24)
    object History : RPSScreens("history", R.string.history, R.drawable.ic_history_white_24dp)
    companion object{
        val values = listOf(Play, History)
    }
}
