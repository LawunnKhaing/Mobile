package com.example.level5example.respository

import android.util.Log
import com.example.level5example.data.api.NumbersApi
import com.example.level5example.data.api.NumbersApiService
import com.example.level5example.data.api.util.Resource
import com.example.level5example.data.model.Number
import kotlinx.coroutines.time.withTimeout
import kotlinx.coroutines.withTimeout

class NumbersRepository {
    private val _numbersApiService: NumbersApiService = NumbersApi.createApi()

    /**
     * suspend function that calls a suspend function from the numbersApi call
     * @return result wrapped in our own Resource sealed class
     */
    suspend fun getRandomNumber(): Resource<Number> {

        val response = try {
            withTimeout(5_000) {
                _numbersApiService.getRandomNumber()
            }
        } catch (e: Exception) {
            Log.e("NumbersRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred while fetching data from the numbersapi.")
        }

        return Resource.Success(response)
    }
}