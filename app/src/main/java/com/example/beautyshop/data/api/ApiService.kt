package com.example.beautyshop.data.api

import com.example.beautyshop.data.models.*
import com.example.beautyshop.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login/")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("registration/")
    suspend fun registration(@Body body: RegistrationRequest): MessageResponse

    @GET("account/")
    fun getProfile(): Call<ProfileModel>

    @POST("account/image/")
    fun updateImageProfile(
        @Body body: UpdateImageProfileModel
    ): Call<Any>

    @GET("services/")
    fun getServices(): Call<SectionResponse>

    @GET("users/")
    fun getUsers(@Query("role") role: Int): Call<UsersResponse>

    @POST("users/")
    fun updateRole(@Body body: UpdateRoleUserRequest): Call<MessageResponse>

    @POST("sections/")
    fun createSection(@Body body: SectionRequest): Call<MessageResponse>

    @POST("sections/edit/")
    fun editSection(@Body body: SectionRequest): Call<MessageResponse>

    @POST("sections/delete/")
    fun removeSection(@Body body: SectionRequest): Call<MessageResponse>

    @GET("sections/master/")
    fun getMastersWithoutSection(): Call<MutableList<ProfileModel>>

    @POST("sections/master/")
    fun addMaster(@Body body: SectionMasterRequest): Call<MessageResponse>

    @POST("sections/master/delete/")
    fun removeMaster(@Body body: SectionMasterRequest): Call<MessageResponse>

    @POST("services/")
    fun createService(@Body serviceRequest: ServiceRequest): Call<MessageResponse>

    @POST("services/edit/")
    fun editService(@Body editServiceRequest: EditServiceRequest): Call<MessageResponse>

    @POST("services/delete/")
    fun removeService(@Body serviceRequest: EditServiceRequest): Call<MessageResponse>

    @GET("appointments/")
    fun getAppointments(): Call<AppointmentResponse>

    @POST("appointments/")
    fun createAppointment(@Body body: CreateAppointmentRequest): Call<MessageResponse>

    @GET("appointments/cancel/")
    fun cancelAppointment(@Query("appointment_id") appointmentId: Int): Call<MessageResponse>

    @GET("schedule/")
    fun getSchedules(): Call<ScheduleResponse>

    @POST("schedule/")
    fun createSchedule(@Body body: ScheduleRequest): Call<MessageResponse>

    @GET("works/")
    fun getWorkOfMaster(@Query("user_id") userId: Int): Call<WorkOfMasterResponse>

    @POST("works/")
    fun addWork(@Body body: AddWorkRequest): Call<MessageResponse>

    @GET("works/delete/")
    fun removeWork(@Query("work_id") workId: Int): Call<MessageResponse>
}