package com.example.level5task1.data.model

import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("createdAt") val creationDate: String,
    @SerializedName("_id") val urlExtension: String
)
