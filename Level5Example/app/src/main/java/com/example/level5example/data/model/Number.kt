package com.example.level5example.data.model

import com.google.gson.annotations.SerializedName

data class Number(
    @SerializedName("text") val text: String,
    @SerializedName("number") val number: Long,
    @SerializedName("found") val found: Boolean,

    // Numbersapi.com supports as number types: trivia, math, date and year.
    // Any other type generates an error.
    @SerializedName("type") val type: String
)

