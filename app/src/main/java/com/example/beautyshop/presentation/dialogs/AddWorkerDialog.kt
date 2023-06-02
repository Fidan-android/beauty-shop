package com.example.beautyshop.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.databinding.DialogAddWorkerBinding

interface IAddWorkerDialog {
    fun onAccept()
}

class AddWorkerDialog(
    private val delegate: IAddWorkerDialog
) : DialogFragment() {

    private var _binding: DialogAddWorkerBinding? = null
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
        _binding = DialogAddWorkerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.root.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.btnAddMaster.setOnClickListener {
            delegate.onAccept()
            dismissAllowingStateLoss()
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.setOnClickListener(null)
        binding.btnAddMaster.setOnClickListener(null)
    }
}