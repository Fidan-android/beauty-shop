package com.example.beautyshop.presentation.master.schedules

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.ScheduleModel
import com.example.beautyshop.data.models.SectionResponse
import com.example.beautyshop.data.models.ServiceModel
import com.example.beautyshop.models.ScheduleRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel(), IScheduleViewModel {
    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val schedules: MutableLiveData<MutableList<ScheduleModel>> = MutableLiveData()
    private val services: MutableLiveData<MutableList<ServiceModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = schedules
    override fun onGetServices() = services

    override fun onGetIsError() = isErrorLiveData

    override fun onLoadData() {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.getSchedules().execute()
                schedules.postValue(response.body()?.schedules ?: mutableListOf())

                val servicedResponse = ApiHelper.getServices().execute()
                val body = servicedResponse.body() as SectionResponse
                if (body.message.isNullOrEmpty()) {
                    services.postValue(body.sections?.first()?.services ?: mutableListOf())
                } else {
                    isErrorLiveData.postValue(body.message ?: "")
                }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }

    override fun onCreateSchedule(serviceId: Int, dateTime: String) {
        MainScope().launch(Dispatchers.IO) {
            try {
                ApiHelper.createSchedule(ScheduleRequest(serviceId, dateTime)).execute()
                onLoadData()
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }
}