package com.example.level5task1.ui.theme.screens

import androidx.annotation.StringRes
import com.example.level5task1.R

sealed class PetsScreens(val route: String, @StringRes val labelResourceId: Int, val customIconImage: Int) {
    object CatsScreen : PetsScreens("cats", R.string.cats, R.drawable.cat_icon)
    object DogsScreen : PetsScreens("dogs", R.string.dogs, R.drawable.dog_icon)
}
