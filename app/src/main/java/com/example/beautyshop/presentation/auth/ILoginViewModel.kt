package com.example.beautyshop.presentation.auth

import androidx.lifecycle.MutableLiveData

interface ILoginViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetIsSuccess(): MutableLiveData<String>
    fun onGetIsError(): MutableLiveData<String>
    fun onLogin(login: String, password: String)
}
