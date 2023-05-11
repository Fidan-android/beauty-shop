package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class WorkerModel(
    @SerializedName("id") val id: Int,
    @SerializedName("first_name") val family: String,
    @SerializedName("middle_name") val name: String,
    @SerializedName("last_name") val patronymic: String,
    @SerializedName("worker_role") val workerRole: String
)
