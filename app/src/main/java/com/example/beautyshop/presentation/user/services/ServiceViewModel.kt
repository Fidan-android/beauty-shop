package com.example.beautyshop.presentation.user.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.data.models.SectionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ServiceViewModel : ViewModel(), IServiceViewModel {
    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val servicesLiveData: MutableLiveData<MutableList<SectionModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = servicesLiveData
    override fun onGetIsError() = isErrorLiveData

    override fun onLoadData() {
        try {
            MainScope().launch(Dispatchers.IO) {
                try {
                    val response = ApiHelper.getServices().execute()
                    val body = response.body() as SectionResponse
                    if (body.message.isNullOrEmpty()) {
                        servicesLiveData.postValue(body.sections ?: mutableListOf())
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
}