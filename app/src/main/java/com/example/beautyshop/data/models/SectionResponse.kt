package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class SectionResponse(
    @SerializedName("sections") val sections: MutableList<SectionModel>? = null,
    @SerializedName("message") val message: String? = null
)
