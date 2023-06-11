package com.example.beautyshop.presentation.auth.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.Properties
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.helper.isEmailValid
import com.example.beautyshop.models.RegistrationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel(), IRegistrationViewModel {
    private val isSuccess: MutableLiveData<String> = MutableLiveData()
    private val isError: MutableLiveData<String> = MutableLiveData()
    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onGetIsLoad() = isProgress
    override fun onGetIsSuccess() = isSuccess
    override fun onGetIsError() = isError

    override fun onRegister(
        login: String,
        phone: String,
        firstName: String,
        name: String,
        password: String,
        repeatPassword: String
    ) {
        if (firstName.isEmpty() || name.isEmpty() || login.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            isError.value = "Все поля должны быть заполнены"
            return
        }
        if (!login.isEmailValid()) {
            isError.value = "Неверный формат почты"
            return
        }
        if (!phone.matches(Regex("^(\\+7|8)\\d{10}\$"))) {
            isError.value = "Неверный формат телефона"
            return
        }
        if (password.length < 6) {
            isError.value = "Пароль должен содержать не менее 6 символов"
            return
        }
        if (password != repeatPassword) {
            isError.value = "Пароли должны совпадать"
            return
        }
        if (!Properties.isNetworkConnect) {
            isError.value = "Отсутствует интернет-соединение"
            return
        }
        isProgress.value = true
        MainScope().launch(Dispatchers.IO) {
            val response =
                ApiHelper.registration(
                    RegistrationRequest(
                        email = login,
                        phone = phone,
                        firstName = firstName,
                        name = name,
                        password = password
                    )
                )

            when (response.message) {
                "success" -> isSuccess.postValue("Регистрация прошла успешно")
                else -> {
                    isProgress.postValue(false)
                    isError.postValue("Непредвиденная ошибка")
                }
            }
        }
    }
}