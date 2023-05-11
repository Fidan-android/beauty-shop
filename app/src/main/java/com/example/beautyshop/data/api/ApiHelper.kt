package com.example.beautyshop.data.api

import com.example.beautyshop.models.LoginRequest
import com.example.beautyshop.models.RegistrationRequest

object ApiHelper {
    private val apiService = ApiManager.apiService

    suspend fun login(body: LoginRequest) = apiService.login(body)

    suspend fun registration(body: RegistrationRequest) = apiService.registration(body)
}