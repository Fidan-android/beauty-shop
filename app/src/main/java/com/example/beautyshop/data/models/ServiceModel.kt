package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class ServiceModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val serviceName: String,
    @SerializedName("worker") val worker: WorkerModel,
    @SerializedName("price") val price: Float,
    @SerializedName("time") val time: Int
)
