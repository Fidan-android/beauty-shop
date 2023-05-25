package com.example.beautyshop.presentation.user.workers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.UsersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class WorkerViewModel : ViewModel(), IWorkerViewModel {

    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val workersLiveData: MutableLiveData<MutableList<ProfileModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = workersLiveData
    override fun onGetIsError() = isErrorLiveData

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
}