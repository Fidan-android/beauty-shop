package com.example.beautyshop.presentation.user.services

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.SectionModel

interface IServiceViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<SectionModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
}