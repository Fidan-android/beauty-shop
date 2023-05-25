package com.example.beautyshop.presentation.user.service_page.create_appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.databinding.DialogAddAppointmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class AddAppointmentDialog(
    private val delegate: IAddAppointmentDialog
) : DialogFragment() {

    interface IAddAppointmentDialog {
        fun onAccept(phone: String)
    }

    private var _binding: DialogAddAppointmentBinding? = null
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
        _binding = DialogAddAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MainScope().launch(Dispatchers.IO) {

        }
        binding.root.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.btnCreateAppointment.setOnClickListener {
            if (binding.etPhone.text.toString().isNotEmpty()) {
                delegate.onAccept(binding.etPhone.text.toString())
                dismissAllowingStateLoss()
            } else {
                Snackbar.make(binding.root, "Введите телефон", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.setOnClickListener(null)
        binding.btnCreateAppointment.setOnClickListener(null)
    }
}