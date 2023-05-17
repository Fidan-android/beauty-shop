package com.example.beautyshop.models

import com.google.gson.annotations.SerializedName

data class SectionRequest(
    @SerializedName("section_id") val sectionId: Int? = null,
    @SerializedName("section_name") val sectionName: String? = null
)