package com.example.beautyshop.presentation.user.service_page

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.ScheduleModel
import com.example.beautyshop.data.models.SectionModel
import java.util.*

interface IServicePageViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<SectionModel>>
    fun onGetProfileData(): MutableLiveData<ProfileModel>
    fun onGetSchedules(): MutableLiveData<MutableList<ScheduleModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onGetIsSuccess(): MutableLiveData<String>
    fun onGetScheduleTimes(serviceId: Int, date: Date): MutableList<ScheduleModel>
    fun onLoadData()
    fun onCreateAppointment(scheduleId: Int, phone: String)
}