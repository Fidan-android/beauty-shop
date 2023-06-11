package com.example.beautyshop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.*
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat


class RenderAdapter<T>(private val viewType: Int, private val delegate: IItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), IRenderAdapter<T> {

    interface IItemClickListener {
        fun onClick(position: Int)
    }

    private var renderList: MutableList<T> = mutableListOf()

    override fun getItemViewType(position: Int) = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RenderViewType.WorkersViewType.viewType -> WorkersViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.master_cell, parent, false)
            )
            RenderViewType.SectionsViewType.viewType -> SectionsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.section_cell, parent, false)
            )
            RenderViewType.ServicesViewType.viewType -> ServicesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.service_cell, parent, false)
            )
            RenderViewType.AdminSectionsViewType.viewType -> AdminSectionsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.admin_section_cell, parent, false)
            )
            RenderViewType.AdminSectionServicesViewType.viewType -> AdminSectionServicesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.admin_service_cell, parent, false)
            )
            RenderViewType.AppointmentsViewType.viewType -> AppointmentsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.appointment_cell, parent, false)
            )
            RenderViewType.MasterAppointmentsViewType.viewType -> MasterAppointmentsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.master_appointment_cell, parent, false)
            )
            RenderViewType.MasterSchedulesViewType.viewType -> MasterScheduleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.master_schedule_cell, parent, false)
            )
            RenderViewType.ScheduleTimesViewType.viewType -> ScheduleTimesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.time_cell, parent, false)
            )
            RenderViewType.WorkOfMasterViewType.viewType -> WorkOfMasterViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.work_cell, parent, false)
            )
            RenderViewType.WorksViewType.viewType -> WorksViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.master_work_cell, parent, false)
            )
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WorkersViewHolder -> holder.onBind(
                renderList[position] as ProfileModel,
                delegate::onClick
            )
            is SectionsViewHolder -> holder.onBind(
                renderList[position] as SectionModel,
                delegate::onClick
            )
            is ServicesViewHolder -> holder.onBind(
                renderList[position] as ServiceModel,
                delegate::onClick
            )
            is AdminSectionsViewHolder -> holder.onBind(
                renderList[position] as SectionModel,
                delegate::onClick
            )
            is AdminSectionServicesViewHolder -> holder.onBind(
                renderList[position] as ServiceModel,
                delegate::onClick
            )
            is AppointmentsViewHolder -> holder.onBind(
                renderList[position] as AppointmentModel,
                delegate::onClick
            )
            is MasterAppointmentsViewHolder -> holder.onBind(
                renderList[position] as AppointmentModel,
                delegate::onClick
            )
            is MasterScheduleViewHolder -> holder.onBind(
                renderList[position] as ScheduleModel,
                delegate::onClick
            )
            is ScheduleTimesViewHolder -> holder.onBind(
                renderList[position] as ScheduleModel,
                delegate::onClick
            )
            is WorkOfMasterViewHolder -> holder.onBind(
                renderList[position] as WorkModel,
                delegate::onClick
            )
            is WorksViewHolder -> holder.onBind(
                renderList[position] as WorkModel,
                delegate::onClick
            )
        }
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = renderList.size

    override fun onUpdateItems(list: MutableList<T>) {
        renderList.clear()
        renderList.addAll(list)
        notifyDataSetChanged()
    }

    open class WorkersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rootView: MaterialCardView = itemView.findViewById(R.id.rootView)
        private val masterAvatar: AppCompatImageView = itemView.findViewById(R.id.masterAvatar)
        private val masterFullName: AppCompatTextView =
            itemView.findViewById(R.id.masterFullName)
        private val masterRole: AppCompatTextView = itemView.findViewById(R.id.masterRole)

        @SuppressLint("SetTextI18n")
        open fun onBind(model: ProfileModel, onClick: (Int) -> Unit) {
            masterFullName.text =
                model.firstName + " " + model.name
            masterRole.text = model.sectionName ?: ""

            Glide
                .with(itemView.context)
                .load(
                    GlideUrl(
                        "http://c95130nt.beget.tech/api/v1/img/" + model.image,
                        LazyHeaders.Builder()
                            .addHeader("User-Agent", "Mozilla/5.0")
                            .build()
                    )
                )
                .centerCrop()
                .error(R.drawable.sample_avatar)
                .into(masterAvatar)

            rootView.setOnClickListener {
                onClick(model.id!!)
            }
        }
    }

    open class SectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //region Views
        private val sectionName: AppCompatTextView = itemView.findViewById(R.id.sectionName)
        private val rvServices: RecyclerView = itemView.findViewById(R.id.rvServices)
        //endregion

        @SuppressLint("SetTextI18n")
        open fun onBind(model: SectionModel, onClick: (Int) -> Unit) {
            sectionName.text = model.sectionName
            val adapter: RenderAdapter<ServiceModel> = RenderAdapter(
                RenderViewType.ServicesViewType.viewType,
                object : IItemClickListener {
                    override fun onClick(position: Int) {
                        onClick(position)
                    }
                })

            rvServices.adapter = adapter
            adapter.onUpdateItems(model.services)
        }
    }

    open class ServicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rootView: MaterialCardView = itemView.findViewById(R.id.rootView)
        private val serviceName: AppCompatTextView = itemView.findViewById(R.id.serviceName)
        private val servicePrice: AppCompatTextView = itemView.findViewById(R.id.servicePrice)
        private val serviceHours: AppCompatTextView = itemView.findViewById(R.id.serviceHours)

        @SuppressLint("SetTextI18n")
        open fun onBind(model: ServiceModel, onClick: (Int) -> Unit) {
            serviceName.text = model.serviceName
            servicePrice.text = model.price
            serviceHours.text = "${model.time} ${model.measurement}."

            rootView.setOnClickListener {
                onClick(model.id)
            }
        }
    }

    open class AdminSectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val sectionName: AppCompatTextView = itemView.findViewById(R.id.sectionName)
        private val editSection: AppCompatImageView = itemView.findViewById(R.id.editSection)
        private val moreActions: AppCompatImageView = itemView.findViewById(R.id.moreActions)

        @SuppressLint("SetTextI18n")
        open fun onBind(model: SectionModel, onClick: (Int) -> Unit) {
            sectionName.text = model.sectionName

            editSection.setOnClickListener {
                onClick(model.id * -1)
            }
            moreActions.setOnClickListener {
                onClick(model.id)
            }
        }
    }

    open class AdminSectionServicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serviceName: AppCompatTextView = itemView.findViewById(R.id.serviceName)
        private val btnEditService: AppCompatImageView = itemView.findViewById(R.id.btnEditService)
        private val btnDeleteService: AppCompatImageView =
            itemView.findViewById(R.id.btnDeleteService)

        @SuppressLint("SetTextI18n")
        open fun onBind(model: ServiceModel, onClick: (Int) -> Unit) {
            serviceName.text = model.serviceName
            btnEditService.setOnClickListener {
                onClick(model.id)
            }
            btnDeleteService.setOnClickListener {
                onClick(model.id - 9999)
            }
        }
    }

    open class AppointmentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userName: AppCompatTextView = itemView.findViewById(R.id.userName)
        private val serviceInfo: AppCompatTextView = itemView.findViewById(R.id.serviceInfo)
        private val serviceName: AppCompatTextView = itemView.findViewById(R.id.serviceName)
        private val servicePrice: AppCompatTextView = itemView.findViewById(R.id.servicePrice)
        private val btnCancelAppointment: AppCompatButton =
            itemView.findViewById(R.id.btnCancelAppointment)

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        open fun onBind(model: AppointmentModel, onClick: (Int) -> Unit) {
            userName.text = "${model.user}  ${model.phone}"
            serviceInfo.text = itemView.context.getString(
                R.string.appointment_to,
                model.master,
                SimpleDateFormat("dd MMMM yyyy HH:mm").format(
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(model.scheduleTime)!!
                )
            )
            serviceName.text = itemView.context.getString(R.string.service_name, model.serviceName)
            servicePrice.text =
                itemView.context.getString(R.string.service_price, model.servicePrice)

            btnCancelAppointment.setOnClickListener {
                onClick(model.appointmentId)
            }
        }
    }

    open class MasterAppointmentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userName: AppCompatTextView = itemView.findViewById(R.id.userName)
        private val serviceInfo: AppCompatTextView = itemView.findViewById(R.id.serviceInfo)
        private val serviceName: AppCompatTextView = itemView.findViewById(R.id.serviceName)
        private val servicePrice: AppCompatTextView = itemView.findViewById(R.id.servicePrice)

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        open fun onBind(model: AppointmentModel, onClick: (Int) -> Unit) {
            userName.text = "${model.user}  ${model.phone}"
            serviceInfo.text = itemView.context.getString(
                R.string.master_appointment_to,
                SimpleDateFormat("dd MMMM в HH:mm").format(
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(model.scheduleTime)!!
                )
            )
            serviceName.text = itemView.context.getString(R.string.service_name, model.serviceName)
            servicePrice.text =
                itemView.context.getString(R.string.service_price, model.servicePrice)
        }
    }

    open class MasterScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val scheduleInfo: AppCompatTextView = itemView.findViewById(R.id.scheduleInfo)
        private val scheduleTime: AppCompatTextView = itemView.findViewById(R.id.scheduleTime)
        private val servicePrice: AppCompatTextView = itemView.findViewById(R.id.servicePrice)
        private val serviceDuration: AppCompatTextView = itemView.findViewById(R.id.serviceDuration)

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        open fun onBind(model: ScheduleModel, onClick: (Int) -> Unit) {
            scheduleInfo.text = itemView.context.getString(R.string.service_name, model.serviceName)
            scheduleTime.text = itemView.context.getString(
                R.string.service_time,
                SimpleDateFormat("dd MMMM в HH:mm").format(
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                        model.time
                    )!!
                )
            )
            servicePrice.text =
                itemView.context.getString(R.string.service_price, model.servicePrice)
            serviceDuration.text =
                itemView.context.getString(R.string.service_duration, model.duration)
        }
    }

    open class ScheduleTimesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textDay: AppCompatTextView = itemView.findViewById(R.id.textDay)

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        open fun onBind(model: ScheduleModel, onClick: (Int) -> Unit) {
            textDay.text =
                SimpleDateFormat("HH:mm").format(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(model.time)!!)

            textDay.setOnClickListener {
                onClick(model.id)
            }
        }
    }

    open class WorkOfMasterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val photoView: AppCompatImageView = itemView.findViewById(R.id.photoView)
        private val description: AppCompatTextView = itemView.findViewById(R.id.description)

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        open fun onBind(model: WorkModel, onClick: (Int) -> Unit) {
            description.text = model.description
            description.isSelected = true
            Glide
                .with(itemView.context)
                .load(
                    GlideUrl(
                        "http://c95130nt.beget.tech/api/v1/works/img/" + model.photo,
                        LazyHeaders.Builder()
                            .addHeader("User-Agent", "Mozilla/5.0")
                            .build()
                    )
                )
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .error(R.drawable.sample_avatar)
                .into(photoView)
        }
    }

    open class WorksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val photoView: AppCompatImageView = itemView.findViewById(R.id.photoView)
        private val description: AppCompatTextView = itemView.findViewById(R.id.description)
        private val btnDeleteWork: AppCompatImageView = itemView.findViewById(R.id.btnDeleteWork)

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        open fun onBind(model: WorkModel, onClick: (Int) -> Unit) {
            description.text = model.description
            description.isSelected = true
            Glide
                .with(itemView.context)
                .load(
                    GlideUrl(
                        "http://c95130nt.beget.tech/api/v1/works/img/" + model.photo,
                        LazyHeaders.Builder()
                            .addHeader("User-Agent", "Mozilla/5.0")
                            .build()
                    )
                )
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .error(R.drawable.sample_avatar)
                .into(photoView)

            btnDeleteWork.setOnClickListener {
                onClick(model.id)
            }
        }
    }
}