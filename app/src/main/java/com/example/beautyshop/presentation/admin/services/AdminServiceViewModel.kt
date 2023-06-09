package com.example.beautyshop.presentation.admin.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.data.models.SectionResponse
import com.example.beautyshop.models.EditServiceRequest
import com.example.beautyshop.models.SectionMasterRequest
import com.example.beautyshop.models.SectionRequest
import com.example.beautyshop.models.ServiceRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AdminServiceViewModel : ViewModel(), IAdminServiceViewModel {

    init {
        onLoadData()
    }

    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val servicesLiveData: MutableLiveData<MutableList<SectionModel>> = MutableLiveData()
    private val workersLiveData: MutableLiveData<MutableList<ProfileModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = servicesLiveData
    override fun onGetWorkers() = workersLiveData
    override fun onGetIsError() = isErrorLiveData

    override fun onLoadData() {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.getServices().execute()
                val body = response.body() as SectionResponse
                if (body.message.isNullOrEmpty()) {
                    servicesLiveData.postValue(body.sections ?: mutableListOf())
                } else {
                    isErrorLiveData.postValue(body.message ?: "")
                }

                val workers = ApiHelper.getMastersWithoutSection().execute()
                if (workers.isSuccessful) {
                    workersLiveData.postValue(workers.body())
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

    override fun onCreateService(
        sectionId: Int,
        serviceName: String,
        servicePrice: Float,
        serviceTime: Float,
        measurement: String
    ) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.createService(
                    ServiceRequest(
                        sectionId,
                        serviceName,
                        servicePrice,
                        serviceTime,
                        measurement
                    )
                ).execute().body()!!

                when (response.message) {
                    "success" -> onLoadData()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onEditService(
        serviceId: Int,
        serviceName: String,
        servicePrice: Float,
        serviceTime: Float,
        measurement: String
    ) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.editService(
                    EditServiceRequest(
                        serviceId,
                        serviceName,
                        servicePrice,
                        serviceTime,
                        measurement
                    )
                ).execute().body()!!

                when (response.message) {
                    "success" -> onLoadData()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onDeleteService(serviceId: Int) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.removeService(
                    EditServiceRequest(serviceId = serviceId)
                ).execute().body()!!

                when (response.message) {
                    "success" -> onLoadData()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onAddMaster(sectionId: Int, userId: Int) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.addMaster(
                    SectionMasterRequest(sectionId = sectionId, userId = userId)
                ).execute().body()!!

                when (response.message) {
                    "success" -> onLoadData()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}