package com.example.beautyshop.presentation.admin.workers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.SectionResponse
import com.example.beautyshop.data.models.ServiceModel
import com.example.beautyshop.data.models.UsersResponse
import com.example.beautyshop.models.ScheduleRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AdminWorkerViewModel : ViewModel(), IAdminWorkerViewModel {

    init {
        onLoadData()
    }

    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val workersLiveData: MutableLiveData<MutableList<ProfileModel>> = MutableLiveData()
    private val services: MutableLiveData<MutableList<ServiceModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    private var selectedMaster = 0

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = workersLiveData
    override fun onGetServices() = services
    override fun onGetIsError() = isErrorLiveData

    override fun onLoadServices(masterId: Int) {
        selectedMaster = masterId
        try {
            MainScope().launch(Dispatchers.IO) {
                try {
                    val response = ApiHelper.getServices(masterId).execute()
                    val body = response.body() as SectionResponse
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
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onLoadData() {
        try {
            MainScope().launch(Dispatchers.IO) {
                try {
                    val response = ApiHelper.getUsers(1).execute()
                    val body = response.body() as UsersResponse
                    if (body.message.isNullOrEmpty()) {
                        workersLiveData.postValue(body.users ?: mutableListOf())
                    } else {
                        isErrorLiveData.postValue(body.message ?: "")
                    }
                } catch (e: Exception) {
                    Log.d("error", e.message.toString())
                    isErrorLiveData.postValue(e.message)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onCreateSchedule(serviceId: Int, dateTime: String) {
        MainScope().launch(Dispatchers.IO) {
            try {
                ApiHelper.createSchedule(ScheduleRequest(selectedMaster, serviceId, dateTime)).execute()
                onLoadData()
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }
}