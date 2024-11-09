package com.example.level5task1.data.model

import com.google.gson.annotations.SerializedName

data class Dog(
    @SerializedName("message") val randomDogPictureUrl: String,
    @SerializedName("status") val status: String
)
