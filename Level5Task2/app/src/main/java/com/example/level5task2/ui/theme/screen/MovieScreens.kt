package com.example.level5task2.ui.theme.screen

sealed class MovieScreens(val route: String) {
    object MovieScreen : MovieScreens("movie_screen")
    object MovieDetailScreen : MovieScreens("movie_detail_screen/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail_screen/$movieId"
    }
}