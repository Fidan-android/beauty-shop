package com.example.beautyshop.presentation.admin.sections

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.data.models.SectionResponse
import com.example.beautyshop.models.SectionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AdminSectionViewModel : ViewModel(), IAdminSectionViewModel {
    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val sectionLiveData: MutableLiveData<MutableList<SectionModel>> =
        MutableLiveData(mutableListOf())
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = sectionLiveData
    override fun onGetIsError() = isErrorLiveData

    override fun onLoadData() {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.getServices().execute()
                val body = response.body() as SectionResponse
                if (body.message.isNullOrEmpty()) {
                    sectionLiveData.postValue(body.sections ?: mutableListOf())
                } else {
                    isErrorLiveData.postValue(body.message ?: "")
                }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }

    override fun onCreateSection(sectionName: String) {
        try {
            MainScope().launch(Dispatchers.IO) {
                val response =
                    ApiHelper.createSection(SectionRequest(sectionName = sectionName)).execute()
                if (response.isSuccessful) {
                    when (response.body()?.message) {
                        "success" -> onLoadData()
                        "error" -> isErrorLiveData.postValue("Что-то пошло не так")
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onEditSection(sectionId: Int, sectionName: String) {
        try {
            MainScope().launch(Dispatchers.IO) {
                val response =
                    ApiHelper.editSection(
                        SectionRequest(
                            sectionId = sectionId,
                            sectionName = sectionName
                        )
                    ).execute()
                if (response.isSuccessful) {
                    when (response.body()?.message) {
                        "success" -> onLoadData()
                        "error" -> isErrorLiveData.postValue("Что-то пошло не так")
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onRemoveSection(sectionId: Int) {
        try {
            MainScope().launch(Dispatchers.IO) {
                val response =
                    ApiHelper.removeSection(SectionRequest(sectionId = sectionId)).execute()
                if (response.isSuccessful) {
                    when (response.body()?.message) {
                        "success" -> onLoadData()
                        "error" -> isErrorLiveData.postValue("Что-то пошло не так")
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}