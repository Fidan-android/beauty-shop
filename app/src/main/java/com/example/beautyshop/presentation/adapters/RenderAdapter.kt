package com.example.beautyshop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.data.models.ServiceModel
import com.example.beautyshop.data.models.WorkerModel
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
            0 -> WorkersViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.master_cell, parent, false)
            )
            1 -> SectionsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.section_cell, parent, false)
            )
            2 -> ServicesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.service_cell, parent, false)
            )
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WorkersViewHolder -> holder.onBind(
                renderList[position] as WorkerModel,
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
        }
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = renderList.size

    override fun onUpdateItems(list: MutableList<T>) {
        val diffCallback = CustomDiffUtil(viewType, renderList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        renderList.clear()
        renderList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    open class WorkersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rootView: MaterialCardView = itemView.findViewById(R.id.rootView)
        private val masterAvatar: AppCompatImageView = itemView.findViewById(R.id.masterAvatar)
        private val masterFullName: AppCompatTextView =
            itemView.findViewById(R.id.masterFullName)
        private val masterRole: AppCompatTextView = itemView.findViewById(R.id.masterRole)

        @SuppressLint("SetTextI18n")
        open fun onBind(model: WorkerModel, onClick: (Int) -> Unit) {
            /*masterFullName.text = model.family + " " + model.name + " " + model.patronymic.first() + "."
            masterRole.text = model.workerRole
            Glide
                .with(itemView.context)
                .load(model.posterUrlPreview)
                .centerCrop()
                .error(R.drawable.logo)
                .into(logoFilm)*/

            rootView.setOnClickListener {
                onClick(model.id)
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
}