package com.example.beautyshop.presentation.auth.register

import androidx.lifecycle.MutableLiveData

interface IRegistrationViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetIsSuccess(): MutableLiveData<String>
    fun onGetIsError(): MutableLiveData<String>
    fun onRegister(
        login: String,
        phone: String,
        firstName: String,
        name: String,
        password: String,
        repeatPassword: String
    )
}
