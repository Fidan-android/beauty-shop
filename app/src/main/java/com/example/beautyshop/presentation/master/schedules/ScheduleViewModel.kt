package com.example.beautyshop.presentation.master.schedules

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.AppointmentModel
import com.example.beautyshop.data.models.ScheduleModel
import com.example.beautyshop.data.models.SectionResponse
import com.example.beautyshop.data.models.ServiceModel
import com.example.beautyshop.models.ScheduleRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel(), IScheduleViewModel {
    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val appointments: MutableLiveData<MutableList<AppointmentModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = appointments
    override fun onGetIsError() = isErrorLiveData

    override fun onLoadData() {
        MainScope().launch(Dispatchers.IO) {
            try {
                val responseAppointments = ApiHelper.getAppointments().execute()
                val body = responseAppointments.body()
                if (body?.message.isNullOrEmpty()) {
                    appointments.postValue(body?.appointments ?: mutableListOf())
                } else {
                    isErrorLiveData.postValue(body?.message ?: "")
                }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }
}