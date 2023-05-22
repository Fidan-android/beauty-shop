package com.example.beautyshop.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.Properties
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.models.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(), ILoginViewModel {

    private val isLoadLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isSuccessLiveData: MutableLiveData<String> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isLoadLiveData
    override fun onGetIsSuccess() = isSuccessLiveData
    override fun onGetIsError() = isErrorLiveData

    override fun onLogin(login: String, password: String) {
        if (login.isEmpty() || password.isEmpty()) {
            isErrorLiveData.value = "Все поля должны быть заполнены"
            return
        }
        if (!Properties.isNetworkConnect) {
            isErrorLiveData.value = "Отсутствует интернет-соединение"
            return
        }
        isLoadLiveData.value = true
        MainScope().launch(Dispatchers.IO) {
            val response = ApiHelper.login(LoginRequest(login, password))
            response.token?.let {
                isSuccessLiveData.postValue(it)
            } ?: when (response.message) {
                "uncorrected" -> {
                    isLoadLiveData.postValue(false)
                    isErrorLiveData.postValue("Неверный логин или пароль")
                }
                else -> {
                    isLoadLiveData.postValue(false)
                    isErrorLiveData.postValue("Непредвиденная ошибка")
                }
            }
        }
    }
}