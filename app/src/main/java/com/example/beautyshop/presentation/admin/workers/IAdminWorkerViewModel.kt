package com.example.beautyshop.presentation.admin.workers

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.ServiceModel

interface IAdminWorkerViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<ProfileModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun onLoadServices(masterId: Int)
    fun onGetServices(): MutableLiveData<MutableList<ServiceModel>>
    fun onCreateSchedule(serviceId: Int, dateTime: String)
}