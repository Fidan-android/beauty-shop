package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    @SerializedName("appointments") val appointments: MutableList<AppointmentModel>? = null,
    @SerializedName("message") val message: String? = null
)
