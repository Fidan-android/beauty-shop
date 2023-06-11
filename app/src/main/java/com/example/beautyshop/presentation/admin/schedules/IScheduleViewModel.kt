package com.example.beautyshop.presentation.admin.schedules

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.SectionModel

interface IScheduleViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<SectionModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
}