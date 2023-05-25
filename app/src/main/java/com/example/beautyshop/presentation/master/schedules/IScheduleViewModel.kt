package com.example.beautyshop.presentation.master.schedules

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ScheduleModel
import com.example.beautyshop.data.models.ServiceModel

interface IScheduleViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<ScheduleModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun onGetServices(): MutableLiveData<MutableList<ServiceModel>>
    fun onCreateSchedule(serviceId: Int, dateTime: String)
}