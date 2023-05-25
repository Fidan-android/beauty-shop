package com.example.beautyshop.models

import com.google.gson.annotations.SerializedName

data class ServiceRequest(
    @SerializedName("section_id") val sectionId: Int? = null,
    @SerializedName("name") val sectionName: String? = null,
    @SerializedName("price") val servicePrice: Float? = null,
    @SerializedName("time") val serviceTime: Float? = null,
    @SerializedName("measurement") val measurement: String? = null
)