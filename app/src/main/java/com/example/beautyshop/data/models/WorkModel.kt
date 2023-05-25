package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class WorkModel(
    @SerializedName("id") val id: Int,
    @SerializedName("photo") val photo: String,
    @SerializedName("description") val description: String
)
