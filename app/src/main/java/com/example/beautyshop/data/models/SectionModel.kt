package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class SectionModel(
    @SerializedName("section_id") val id: Int,
    @SerializedName("section_name") val sectionName: String,
    @SerializedName("master") val masterId: Int? = null,
    @SerializedName("services") val services: MutableList<ServiceModel>
)
