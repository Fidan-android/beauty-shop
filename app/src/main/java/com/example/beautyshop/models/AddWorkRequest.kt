package com.example.beautyshop.models

import com.google.gson.annotations.SerializedName

data class AddWorkRequest(
    @SerializedName("file") val file: String,
    @SerializedName("description") val description: String? = null
)
