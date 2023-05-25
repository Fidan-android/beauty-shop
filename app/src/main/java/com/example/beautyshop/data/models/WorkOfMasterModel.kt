package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class WorkOfMasterModel(
    @SerializedName("master") val masterName: String,
    @SerializedName("master_avatar") val masterAvatar: String,
    @SerializedName("works") val works: MutableList<WorkModel>
)
