package com.example.beautyshop.presentation.admin.schedules.schedule_page

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshop.data.api.ApiHelper
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.ScheduleModel
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.data.models.SectionResponse
import com.example.beautyshop.models.CreateAppointmentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SchedulePageViewModel : ViewModel(), ISchedulePageViewModel {

    init {
        onLoadData()
    }

    private val isProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    private val sectionsLiveData: MutableLiveData<MutableList<SectionModel>> = MutableLiveData()
    private val profileLiveData: MutableLiveData<ProfileModel> = MutableLiveData()
    private val scheduleLiveData: MutableLiveData<MutableList<ScheduleModel>> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<String> = MutableLiveData()
    private val isSuccessLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onGetIsLoad() = isProgress
    override fun onGetData() = sectionsLiveData
    override fun onGetProfileData() = profileLiveData
    override fun onGetSchedules() = scheduleLiveData
    override fun onGetIsError() = isErrorLiveData
    override fun onGetIsSuccess() = isSuccessLiveData

    @SuppressLint("SimpleDateFormat")
    override fun onGetScheduleTimes(serviceId: Int, date: Date): MutableList<ScheduleModel> {
        val tempList: MutableList<ScheduleModel> = mutableListOf()
        scheduleLiveData.value?.map { model ->
            if (model.serviceId == serviceId) {
                val left = Calendar.getInstance().apply {
                    time =
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(model.time)!!
                }
                val right = Calendar.getInstance().apply {
                    time = date
                }

                if (left.get(Calendar.YEAR) == right.get(Calendar.YEAR) && left.get(Calendar.MONTH) == right.get(
                        Calendar.MONTH
                    ) && left.get(Calendar.DAY_OF_MONTH) == right.get(Calendar.DAY_OF_MONTH)
                ) {
                    tempList.add(model)
                }
            }
        }
        return tempList
    }

    override fun onLoadData() {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response = ApiHelper.getServices().execute()
                sectionsLiveData.postValue(
                    (response.body() as SectionResponse).sections ?: mutableListOf()
                )

                val profile = ApiHelper.getProfile().execute()
                profileLiveData.postValue(profile.body())

                val schedule = ApiHelper.getSchedules().execute()
                scheduleLiveData.postValue(schedule.body()?.schedules ?: mutableListOf())
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }
    }

    override fun onCreateAppointment(scheduleId: Int) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val response =
                    ApiHelper.createAppointment(CreateAppointmentRequest(scheduleId))
                        .execute()
                if (response.body()?.message != "success") {
                    isErrorLiveData.postValue(response.body()?.message ?: "")
                } else {
                    isSuccessLiveData.postValue("Запись успешно оформлена")
                }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
                isErrorLiveData.postValue(e.message)
            }
        }.invokeOnCompletion {
            onLoadData()
        }
    }
}