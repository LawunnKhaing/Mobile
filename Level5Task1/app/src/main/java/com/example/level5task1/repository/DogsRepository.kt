package com.example.level5task1.repository


import com.example.level5task1.data.api.Api
import com.example.level5task1.data.model.Dog

class DogsRepository {
    suspend fun getRandomDog(): Resource<Dog> {
        return try {
            val response = Api.dogsClient.getRandomDog()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Something went wrong!")
        }
    }
}
