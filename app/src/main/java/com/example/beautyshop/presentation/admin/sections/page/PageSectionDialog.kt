package com.example.beautyshop.presentation.admin.sections.page

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.databinding.DialogPageSectionBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class PageSectionDialog(
    private val model: SectionModel,
    private val delegate: IPageSectionDialog
) : DialogFragment() {

    interface IPageSectionDialog {
        fun onEdit(sectionId: Int, sectionName: String)
        fun onDelete(sectionId: Int)
    }

    private var _binding: DialogPageSectionBinding? = null
    private val binding get() = _binding!!

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
        _binding = DialogPageSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSectionName.text = Editable.Factory().newEditable(model.sectionName)
        /*if (model.masterId != null) {
            binding.btnAddMaster.visibility = View.GONE
        } else {
            binding.btnAddMaster.visibility = View.VISIBLE
        }*/
    }

    override fun onStart() {
        super.onStart()
        MainScope().launch(Dispatchers.IO) {

        }
        binding.root.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.btnRemoveSection.setOnClickListener {
            delegate.onDelete(model.id)
            dismissAllowingStateLoss()
        }

        binding.btnSaveSection.setOnClickListener {
            if (binding.etSectionName.text.toString().isNotEmpty()) {
                delegate.onEdit(model.id, binding.etSectionName.text.toString())
                dismissAllowingStateLoss()
            } else {
                Snackbar.make(binding.root, "Введите наименование секции", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.setOnClickListener(null)
        binding.btnRemoveSection.setOnClickListener(null)
        binding.btnSaveSection.setOnClickListener(null)
    }
}