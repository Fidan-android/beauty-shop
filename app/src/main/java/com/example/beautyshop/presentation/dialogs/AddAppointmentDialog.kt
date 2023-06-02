package com.example.beautyshop.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.databinding.TextDialogWithYesNoBinding


interface ITextDialogWithYesNo {
    fun onAccept()
}

class TextDialogWithYesNo(
    private val titleDialog: String,
    private val delegate: ITextDialogWithYesNo
) : DialogFragment() {

    private var _binding: TextDialogWithYesNoBinding? = null
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
        _binding = TextDialogWithYesNoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleDialog.text = titleDialog
    }

    override fun onStart() {
        super.onStart()
        binding.btnYes.setOnClickListener {
            delegate.onAccept()
            dismissAllowingStateLoss()
        }
        binding.btnNo.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.setOnClickListener(null)
        binding.btnYes.setOnClickListener(null)
        binding.btnNo.setOnClickListener(null)
    }
}