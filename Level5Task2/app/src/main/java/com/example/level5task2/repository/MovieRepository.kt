package com.example.level5task2.repository

import com.example.level5task2.data.api.MovieApiService
import com.example.level5task2.data.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {
    private val apiService: MovieApiService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(MovieApiService::class.java)
    }

    suspend fun searchMovies(query: String): List<Movie> {
        val response = apiService.searchMovies("61bc5f4d9103d329bf4f459d171bf28d", query)
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }
}