package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class AppointmentModel(
    @SerializedName("id") val appointmentId: Int,
    @SerializedName("phone") val phone: String,
    @SerializedName("name") val serviceName: String,
    @SerializedName("price") val servicePrice: String,
    @SerializedName("time") val scheduleTime: String,
    @SerializedName("master") val master: String,
    @SerializedName("master_avatar") val masterAvatar: String,
    @SerializedName("user") val user: String,
    @SerializedName("user_avatar") val userAvatar: String
)
