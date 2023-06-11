package com.example.beautyshop.presentation.master.schedules

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.AppointmentModel
import com.example.beautyshop.data.models.ScheduleModel
import com.example.beautyshop.data.models.ServiceModel

interface IScheduleViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<AppointmentModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
}