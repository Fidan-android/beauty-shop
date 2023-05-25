package com.example.beautyshop.presentation.user.workers.page

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.*
import com.example.beautyshop.models.CreateAppointmentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WorkViewModel : ViewModel(), IWorkViewModel {
    private val worksLiveData: MutableLiveData<MutableList<WorkOfMasterModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetData() = worksLiveData
    override fun onGetIsError() = isErrorLiveData

    override fun onLoadData(userId: Int) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.getWorkOfMaster(userId).execute()
                worksLiveData.postValue(response.body()?.works ?: mutableListOf())

            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }
}