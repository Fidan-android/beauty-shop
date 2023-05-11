package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String? = null,
    @SerializedName("message") val message: String? = null
)
