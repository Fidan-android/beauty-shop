package com.example.beautyshop.presentation.user.workers

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel

interface IWorkerViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<ProfileModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
}