package com.example.beautyshop.models

import com.google.gson.annotations.SerializedName

data class CreateAppointmentRequest(
    @SerializedName("schedule_id") val scheduleId: Int? = null
)