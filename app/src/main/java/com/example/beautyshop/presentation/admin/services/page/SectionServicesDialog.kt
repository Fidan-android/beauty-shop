package com.example.beautyshop.presentation.admin.services.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.data.models.ServiceModel
import com.example.beautyshop.databinding.DialogSectionServicesBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter


class SectionServicesDialog(
    private val model: SectionModel,
    private val delegate: ISectionServicesDialog
) : DialogFragment() {

    interface ISectionServicesDialog {
        fun onCreateService(sectionId: Int)
        fun onAddMaster(sectionId: Int)
        fun onEdit(serviceModel: ServiceModel)
        fun onDelete(serviceId: Int)
    }

    private var _binding: DialogSectionServicesBinding? = null
    private val binding get() = _binding!!
    private val adapter: RenderAdapter<ServiceModel> by lazy {
        RenderAdapter(
            RenderViewType.AdminSectionServicesViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {
                    if (position < 0) {
                        delegate.onDelete(position + 9999)
                        dismissAllowingStateLoss()
                    } else {
                        delegate.onEdit(model.services.first { item -> item.id == position })
                        dismissAllowingStateLoss()
                    }
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        _binding = DialogSectionServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSectionServices.adapter = adapter
        adapter.onUpdateItems(model.services)
    }

    override fun onStart() {
        super.onStart()
        binding.root.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.btnCreateService.setOnClickListener {
            delegate.onCreateService(model.id)
            dismissAllowingStateLoss()
        }

        binding.btnAddMaster.setOnClickListener {
            delegate.onAddMaster(model.id)
            dismissAllowingStateLoss()
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.setOnClickListener(null)
        binding.btnCreateService.setOnClickListener(null)
        binding.btnAddMaster.setOnClickListener(null)
    }
}