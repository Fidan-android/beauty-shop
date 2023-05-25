package com.example.beautyshop.presentation.admin.sections.add_section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.databinding.DialogAddSectionBinding
import com.google.android.material.snackbar.Snackbar


class AddSectionDialog(
    private val delegate: IAddSectionDialog
) : DialogFragment() {

    interface IAddSectionDialog {
        fun onAccept(sectionName: String)
    }

    private var _binding: DialogAddSectionBinding? = null
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
        _binding = DialogAddSectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnCreateSection.setOnClickListener {
            if (binding.etSectionName.text.toString().isNotEmpty()) {
                delegate.onAccept(binding.etSectionName.text.toString())
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
        binding.btnCreateSection.setOnClickListener(null)
    }
}