package com.example.beautyshop.presentation.user.workers.page

import androidx.lifecycle.MutableLiveData
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.ScheduleModel
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.data.models.WorkOfMasterModel
import java.util.Date

interface IWorkViewModel {
    fun onGetData(): MutableLiveData<MutableList<WorkOfMasterModel>>
    fun onGetIsError(): MutableLiveData<String>
    fun onLoadData(userId: Int)
}