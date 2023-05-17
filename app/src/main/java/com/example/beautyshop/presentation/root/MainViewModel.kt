package com.example.beautyshop.presentation.root

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.Properties
import com.example.beautyshop.data.api.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), IMainViewModel {

    private val roleLiveData: MutableLiveData<Int> = MutableLiveData()

    override fun onGetData() = roleLiveData

    override fun onConfigureRole() {
        try {
            MainScope().launch(Dispatchers.IO) {
                val response = ApiHelper.getProfile().execute()
                if (response.isSuccessful) {
                    Properties.profile = response.body()
                }
            }.invokeOnCompletion {
                roleLiveData.postValue(Properties.profile?.role ?: 0)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}