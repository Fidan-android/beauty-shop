package com.example.beautyshop.presentation.admin.services.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.databinding.DialogAddWorkersBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter


class AddWorkersDialog(
    private val workers: MutableList<ProfileModel>,
    private val delegate: IAddWorkersDialog
) : DialogFragment() {

    interface IAddWorkersDialog {
        fun onAddMaster(userId: Int)
    }

    private var _binding: DialogAddWorkersBinding? = null
    private val binding get() = _binding!!
    private val adapter: RenderAdapter<ProfileModel> by lazy {
        RenderAdapter(
            RenderViewType.WorkersViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {
                    delegate.onAddMaster(position)
                    dismissAllowingStateLoss()
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
        _binding = DialogAddWorkersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSectionServices.adapter = adapter
        adapter.onUpdateItems(workers)
    }

    override fun onStart() {
        super.onStart()
        binding.root.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.setOnClickListener(null)
    }
}