package com.example.beautyshop.presentation.admin.workers

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel

interface IAdminWorkerViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<ProfileModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun onMoveWorker(userId: Int)
}