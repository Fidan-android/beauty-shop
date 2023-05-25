package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class ScheduleModel(
    @SerializedName("id") val id: Int,
    @SerializedName("time") val time: String,
    @SerializedName("service_id") val serviceId: Int,
    @SerializedName("name") val serviceName: String,
    @SerializedName("price") val servicePrice: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("master") val master: String,
    @SerializedName("master_avatar") val masterAvatar: String,
)