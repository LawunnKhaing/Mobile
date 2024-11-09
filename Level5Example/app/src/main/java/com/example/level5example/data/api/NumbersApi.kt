package com.example.level5example.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NumbersApi {
    companion object {
        // The base url off the api.
        private const val baseUrl = "http://numbersapi.com/"

        // Numbersapi.com supports as number types: trivia, math, date and year.
        // Any other type generates an error.
        const val numberType = "trivia"
//        const val numberType = "math"
//        const val numberType = "date"
//        const val numberType = "year"

        /**
         * @return [NumbersApiService] The service class off the retrofit client.
         */
        fun createApi(): NumbersApiService {
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val triviaApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit NumbersApiService
            return triviaApi.create(NumbersApiService::class.java)
        }
    }
}