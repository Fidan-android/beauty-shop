package com.example.beautyshop.presentation.master.works

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel

interface IWorkViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<ProfileModel>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
}