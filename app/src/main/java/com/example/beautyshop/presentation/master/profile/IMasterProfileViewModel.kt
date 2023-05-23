package com.example.beautyshop.presentation.master.profile

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.AppointmentModel
import com.example.beautyshop.data.models.ProfileModel

interface IMasterProfileViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<ProfileModel>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun changeImageProfile(path: String)
    fun onGetAppointments(): MutableLiveData<MutableList<AppointmentModel>>
}