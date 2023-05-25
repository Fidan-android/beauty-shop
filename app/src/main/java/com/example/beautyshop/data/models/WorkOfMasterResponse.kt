package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class WorkOfMasterResponse(
    @SerializedName("works") val works: MutableList<WorkOfMasterModel>? = null,
    @SerializedName("message") val message: String? = null
)
