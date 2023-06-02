package com.example.beautyshop.models

import com.google.gson.annotations.SerializedName

data class ScheduleRequest(
    @SerializedName("master_id") val masterId: Int? = null,
    @SerializedName("service_id") val serviceId: Int? = null,
    @SerializedName("time") val dateTime: String? = null
)