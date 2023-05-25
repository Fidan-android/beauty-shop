package com.example.beautyshop.presentation.master.works

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.WorkOfMasterModel
import com.example.beautyshop.models.AddWorkRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class MasterWorkViewModel : ViewModel(), IMasterWorkViewModel {
    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val worksLiveData: MutableLiveData<MutableList<WorkOfMasterModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = worksLiveData
    override fun onGetIsError() = isErrorLiveData

    override fun onLoadData() {
        MainScope().launch(Dispatchers.IO) {
            try {
                val profile = ApiHelper.getProfile().execute()
                val response = ApiHelper.getWorkOfMaster(profile.body()?.id!!).execute()

                if (response.body()?.message.isNullOrEmpty()) {
                    worksLiveData.postValue(response.body()?.works ?: mutableListOf())
                } else {
                    isErrorLiveData.postValue(response.body()?.message ?: "")
                }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }

    override fun onAddWork(path: String, description: String?) {
        MainScope().launch(Dispatchers.IO) {
            try {
                ApiHelper.addWork(
                    AddWorkRequest(
                        Base64.getEncoder().encodeToString(File(path).readBytes()), description
                    )
                ).execute()
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }.invokeOnCompletion {
            onLoadData()

        }
    }

    override fun onRemoveWork(workId: Int) {
        MainScope().launch(Dispatchers.IO) {
            try {
                ApiHelper.removeWork(workId).execute()
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }.invokeOnCompletion {
            worksLiveData.postValue(mutableListOf())
            onLoadData()
        }
    }
}