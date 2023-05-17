package com.example.beautyshop.models

import com.google.gson.annotations.SerializedName

data class EditServiceRequest(
    @SerializedName("service_id") val serviceId: Int? = null,
    @SerializedName("service_name") val sectionName: String? = null,
    @SerializedName("price") val servicePrice: Float? = null,
    @SerializedName("time") val serviceTime: Float? = null
)