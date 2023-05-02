package com.example.beautyshop.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel(), ILoginViewModel {

    private val isLoadLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isSuccessLiveData: MutableLiveData<String> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isLoadLiveData
    override fun onGetIsSuccess() = isSuccessLiveData
    override fun onGetIsError() = isErrorLiveData

    override fun onLogin(login: String, password: String) {
        isLoadLiveData.value = true
        isSuccessLiveData.value = ""
    }
}