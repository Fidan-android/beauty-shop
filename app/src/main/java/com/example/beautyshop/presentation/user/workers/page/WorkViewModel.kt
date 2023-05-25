package com.example.beautyshop.presentation.user.workers.page

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.WorkModel
import com.example.beautyshop.data.models.WorkOfMasterModel
import com.example.beautyshop.data.models.WorkOfMasterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class WorkViewModel : ViewModel(), IWorkViewModel {
    private val masterLiveData: MutableLiveData<WorkOfMasterModel> = MutableLiveData()
    private val worksLiveData: MutableLiveData<MutableList<WorkModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetData() = worksLiveData
    override fun onGetMasterData() = masterLiveData
    override fun onGetIsError() = isErrorLiveData

    override fun onLoadData(userId: Int) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.getWorkOfMaster(userId).execute()
                if (response.body()?.message.isNullOrEmpty()) {
                    val model = response.body() as WorkOfMasterResponse
                    masterLiveData.postValue(model.worksOfMaster!!)
                    worksLiveData.postValue(response.body()?.worksOfMaster?.works ?: mutableListOf())
                } else {
                    isErrorLiveData.postValue(response.body()?.message ?: "")
                }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }
}