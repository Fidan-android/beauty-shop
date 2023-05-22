package com.example.beautyshop.presentation.admin.sections

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.SectionModel

interface IAdminSectionViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<SectionModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun onCreateSection(sectionName: String)
    fun onEditSection(sectionId: Int, sectionName: String)
    fun onRemoveSection(sectionId: Int)
}