package com.example.beautyshop.presentation.user.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.AppointmentModel
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.UpdateImageProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class ProfileViewModel : ViewModel(), IProfileViewModel {
    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val profileLiveData: MutableLiveData<ProfileModel> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()
    private val appointments: MutableLiveData<MutableList<AppointmentModel>> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = profileLiveData
    override fun onGetIsError() = isErrorLiveData
    override fun onGetAppointments() = appointments

    override fun onLoadData() {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.getProfile().execute()
                profileLiveData.postValue(response.body())

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

    override fun changeImageProfile(path: String) {
        MainScope().launch(Dispatchers.IO) {
            ApiHelper.updateImageProfile(
                UpdateImageProfileModel(
                    Base64.getEncoder().encodeToString(File(path).readBytes())
                )
            ).execute()
        }.invokeOnCompletion {
            onLoadData()
        }
    }

    override fun onCancelAppointment(appointmentId: Int) {
        MainScope().launch(Dispatchers.IO) {
            ApiHelper.cancelAppointment(appointmentId).execute()
        }.invokeOnCompletion {
            appointments.postValue(mutableListOf())
            onLoadData()
        }
    }
}