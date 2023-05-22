package com.example.beautyshop.presentation.user.profile

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel

interface IProfileViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<ProfileModel>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun changeImageProfile(path: String)
}