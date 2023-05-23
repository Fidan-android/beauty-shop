package com.example.beautyshop.data.api

import com.example.beautyshop.data.models.UpdateImageProfileModel
import com.example.beautyshop.models.*

object ApiHelper {
    private val apiService = ApiManager.apiService

    suspend fun login(body: LoginRequest) = apiService.login(body)

    suspend fun registration(body: RegistrationRequest) = apiService.registration(body)

    fun getServices() = apiService.getServices()

    fun getProfile() = apiService.getProfile()

    fun updateImageProfile(model: UpdateImageProfileModel) = apiService.updateImageProfile(model)

    fun getUsers(role: Int) = apiService.getUsers(role)

    fun updateRole(model: UpdateRoleUserRequest) = apiService.updateRole(model)

    fun createSection(sectionRequest: SectionRequest) = apiService.createSection(sectionRequest)

    fun editSection(sectionRequest: SectionRequest) = apiService.editSection(sectionRequest)

    fun removeSection(sectionRequest: SectionRequest) = apiService.removeSection(sectionRequest)

    fun createService(serviceRequest: ServiceRequest) = apiService.createService(serviceRequest)

    fun editService(editServiceRequest: EditServiceRequest) =
        apiService.editService(editServiceRequest)

    fun removeService(serviceRequest: EditServiceRequest) = apiService.removeService(serviceRequest)

    fun getMastersWithoutSection() = apiService.getMastersWithoutSection()

    fun addMaster(sectionMasterRequest: SectionMasterRequest) =
        apiService.addMaster(sectionMasterRequest)

    fun removeMaster(sectionMasterRequest: SectionMasterRequest) =
        apiService.removeMaster(sectionMasterRequest)

    fun getAppointments() = apiService.getAppointments()
    fun createAppointment() = apiService.createAppointment()
    fun cancelAppointment(appointmentId: Int) = apiService.cancelAppointment(appointmentId)
}