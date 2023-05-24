package com.example.beautyshop.models

import com.google.gson.annotations.SerializedName

data class ScheduleRequest(
    @SerializedName("service_id") val serviceId: Int? = null,
    @SerializedName("time") val dateTime: String? = null
)