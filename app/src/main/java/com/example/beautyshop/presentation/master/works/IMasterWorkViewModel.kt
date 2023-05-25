package com.example.beautyshop.presentation.master.works

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.WorkModel
import com.example.beautyshop.data.models.WorkOfMasterModel

interface IMasterWorkViewModel {
    fun onGetIsLoad(): MutableLiveData<Boolean>
    fun onGetData(): MutableLiveData<MutableList<WorkModel>>
    fun onGetMasterData(): MutableLiveData<WorkOfMasterModel>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData()
    fun onAddWork(path: String, description: String?)
    fun onRemoveWork(workId: Int)
}