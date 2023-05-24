package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("schedules") val schedules: MutableList<ScheduleModel>? = null,
    @SerializedName("message") val message: String? = null
)
