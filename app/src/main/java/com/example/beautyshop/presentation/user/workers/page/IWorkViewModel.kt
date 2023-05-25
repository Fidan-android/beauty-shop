package com.example.beautyshop.presentation.user.workers.page

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.WorkModel
import com.example.beautyshop.data.models.WorkOfMasterModel

interface IWorkViewModel {
    fun onGetData(): MutableLiveData<MutableList<WorkModel>>
    fun onGetMasterData(): MutableLiveData<WorkOfMasterModel>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData(userId: Int)
}