package com.example.level5task1.repository


import android.util.Log
import com.example.level5task1.data.api.Api
import com.example.level5task1.data.model.Cat

class CatsRepository {
    suspend fun getRandomCat(): Resource<Cat> {
        return try {
            val response = Api.catsClient.getRandomCat()
            Resource.Success(response)
        } catch (e: Exception) {
            Log.e("CatsRepository", "Error fetching random cat", e)
            Resource.Error(e.message ?: "Something went wrong!")
        }
    }
}
