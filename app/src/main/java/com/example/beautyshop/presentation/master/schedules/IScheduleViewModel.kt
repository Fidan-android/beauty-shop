package com.example.beautyshop.presentation.master.schedules

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel

interface IScheduleViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<ProfileModel>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
}