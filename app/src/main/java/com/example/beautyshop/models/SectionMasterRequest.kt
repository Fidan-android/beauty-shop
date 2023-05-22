package com.example.beautyshop.models

import com.google.gson.annotations.SerializedName

data class SectionMasterRequest(
    @SerializedName("section_id") val sectionId: Int? = null,
    @SerializedName("user_id") val userId: Int? = null
)