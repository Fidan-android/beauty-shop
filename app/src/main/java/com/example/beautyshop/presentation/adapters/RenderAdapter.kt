package com.example.beautyshop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.AppointmentModel
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.data.models.ServiceModel
import com.google.android.material.card.MaterialCardView


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
            Glide
                .with(itemView.context)
                .load(model.image)
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
            servicePrice.text = model.price.toString()
            serviceHours.text = "${model.time} час."

            rootView.setOnClickListener {
                onClick(model.id)
            }
        }
    }

    open class AdminSectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val rootView: MaterialCardView = itemView.findViewById(R.id.rootView)
        private val sectionName: AppCompatTextView = itemView.findViewById(R.id.sectionName)

        @SuppressLint("SetTextI18n")
        open fun onBind(model: SectionModel, onClick: (Int) -> Unit) {
            sectionName.text = model.sectionName

            rootView.setOnClickListener {
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
        private val btnCancelAppointment: AppCompatButton = itemView.findViewById(R.id.servicePrice)

        @SuppressLint("SetTextI18n")
        open fun onBind(model: AppointmentModel, onClick: (Int) -> Unit) {
            userName.text = model.user
            serviceInfo.text = itemView.context.getString(R.string.appointment_to, model.master, model.scheduleTime)
            serviceName.text = itemView.context.getString(R.string.service_name, model.serviceName)
            servicePrice.text = itemView.context.getString(R.string.service_price, model.servicePrice)

            btnCancelAppointment.setOnClickListener {
                onClick(model.appointmentId)
            }
        }
    }
}