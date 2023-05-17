package com.example.beautyshop.models

import com.google.gson.annotations.SerializedName

data class UpdateRoleUserRequest(
    @SerializedName("user_id") val userId: Int
)