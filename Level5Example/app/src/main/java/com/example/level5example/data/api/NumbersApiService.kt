package com.example.level5example.data.api

import com.example.level5example.data.model.Number
import retrofit2.http.GET

interface NumbersApiService {
    // The GET method needed to retrieve a random number trivia.
    @GET("/random/${NumbersApi.numberType}?json")
    suspend fun getRandomNumber(): Number
}