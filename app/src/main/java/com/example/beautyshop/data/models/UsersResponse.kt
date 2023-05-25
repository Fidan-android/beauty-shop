package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("users") val users: MutableList<ProfileModel>? = null,
    @SerializedName("message") val message: String? = null
)
