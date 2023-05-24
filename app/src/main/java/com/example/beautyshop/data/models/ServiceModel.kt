package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class ServiceModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val serviceName: String,
    @SerializedName("price") val price: String,
    @SerializedName("time") val time: Float
)
