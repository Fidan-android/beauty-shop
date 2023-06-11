package com.example.beautyshop.presentation.admin.services

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.SectionModel

interface IAdminServiceViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetWorkers(): MutableLiveData<MutableList<ProfileModel>>
    fun onGetData(): MutableLiveData<MutableList<SectionModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun onCreateSection(sectionName: String)
    fun onEditSection(sectionId: Int, sectionName: String)
    fun onRemoveSection(sectionId: Int)
    fun onCreateService(
        sectionId: Int,
        serviceName: String,
        servicePrice: Float,
        serviceTime: Float,
        measurement: String
    )

    fun onEditService(
        serviceId: Int,
        serviceName: String,
        servicePrice: Float,
        serviceTime: Float,
        measurement: String
    )

    fun onDeleteService(serviceId: Int)
    fun onAddMaster(sectionId: Int, userId: Int)
}