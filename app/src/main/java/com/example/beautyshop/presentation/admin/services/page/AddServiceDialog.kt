package com.example.beautyshop.presentation.admin.services.page

import android.location.GnssMeasurement
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.data.models.ServiceModel
import com.example.beautyshop.databinding.DialogAddServiceBinding
import com.google.android.material.snackbar.Snackbar


class AddServiceDialog(
    private val serviceModel: ServiceModel? = null,
    private val delegate: IAddServiceDialog
) : DialogFragment() {

    interface IAddServiceDialog {
        fun onAccept(serviceName: String, price: Float, time: Float, measurement: String)
    }

    private var _binding: DialogAddServiceBinding? = null
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
        _binding = DialogAddServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (serviceModel != null) {
            binding.etServiceName.text = Editable.Factory().newEditable(serviceModel.serviceName)
            binding.etServicePrice.text =
                Editable.Factory().newEditable(serviceModel.price)
            binding.etServiceTime.text =
                Editable.Factory().newEditable(serviceModel.time.toString())

            binding.spinnerTime.setSelection(
                if (serviceModel.measurement == "час") {
                    0
                } else {
                    1
                }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        binding.root.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.btnCreateService.setOnClickListener {
            if (binding.etServiceName.text.toString()
                    .isNotEmpty() && binding.etServicePrice.text.toString()
                    .isNotEmpty() && binding.etServiceTime.text.toString().isNotEmpty()
            ) {
                delegate.onAccept(
                    binding.etServiceName.text.toString(),
                    binding.etServicePrice.text.toString().toFloat(),
                    binding.etServiceTime.text.toString().toFloat(),
                    binding.spinnerTime.selectedItem.toString()
                )
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
        binding.btnCreateService.setOnClickListener(null)
    }
}