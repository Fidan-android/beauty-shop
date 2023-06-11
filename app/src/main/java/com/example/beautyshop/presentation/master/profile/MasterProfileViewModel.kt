package com.example.beautyshop.presentation.master.profile

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

class MasterProfileViewModel : ViewModel(), IMasterProfileViewModel {
    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val profileLiveData: MutableLiveData<ProfileModel> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = profileLiveData
    override fun onGetIsError() = isErrorLiveData

    override fun onLoadData() {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.getProfile().execute()
                profileLiveData.postValue(response.body())
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }

    override fun changeImageProfile(path: String) {
        isErrorLiveData.postValue(Base64.getEncoder().encodeToString(File(path).readBytes()))
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
}