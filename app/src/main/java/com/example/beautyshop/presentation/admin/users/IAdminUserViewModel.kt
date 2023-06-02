package com.example.beautyshop.presentation.admin.users

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.ServiceModel

interface IAdminUserViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<ProfileModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun onMoveWorker(userId: Int)
}