package com.example.beautyshop.presentation.master.works

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.WorkOfMasterModel

interface IMasterWorkViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<WorkOfMasterModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun onAddWork(path: String, description: String?)
    fun onRemoveWork(workId: Int)
}