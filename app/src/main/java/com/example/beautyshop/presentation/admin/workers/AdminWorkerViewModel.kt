package com.example.beautyshop.presentation.admin.workers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.models.UpdateRoleUserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AdminWorkerViewModel : ViewModel(), IAdminWorkerViewModel {

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
                    val response = ApiHelper.getUsers(0).execute()
                    workersLiveData.postValue(response.body() ?: mutableListOf())
                } catch (e: Exception) {
                    Log.d("error", e.message.toString())
                    isErrorLiveData.postValue(e.message)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onMoveWorker(userId: Int) {
        try {
            MainScope().launch(Dispatchers.IO) {
                val response = ApiHelper.updateRole(UpdateRoleUserRequest(userId)).execute()
                if (response.isSuccessful) {
                    onLoadData()
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}