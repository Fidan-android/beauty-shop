package com.example.beautyshop.data.models

import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("role") val role: Int? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("message") val message: String? = null
)
