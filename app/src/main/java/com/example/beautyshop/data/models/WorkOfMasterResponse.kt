package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class WorkOfMasterResponse(
    @SerializedName("works_of_master") val worksOfMaster: WorkOfMasterModel? = null,
    @SerializedName("message") val message: String? = null
)
