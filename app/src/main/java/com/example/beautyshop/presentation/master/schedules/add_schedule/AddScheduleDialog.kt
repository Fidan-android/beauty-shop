package com.example.beautyshop.presentation.master.schedules.add_schedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.ServiceModel
import com.example.beautyshop.databinding.DialogAddScheduleBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddScheduleDialog(
    private val services: MutableList<ServiceModel>,
    private val delegate: IAddScheduleDialog
) : DialogFragment() {

    interface IAddScheduleDialog {
        fun onAccept(serviceId: Int, dateTime: String)
    }

    private var changedService = -1
    private val changedCalendar = Calendar.getInstance()
    private var _binding: DialogAddScheduleBinding? = null
    private val binding get() = _binding!!
    private val adapter: RenderAdapter<ServiceModel> by lazy {
        RenderAdapter(
            RenderViewType.ServicesViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {
                    changedService = position
                    binding.rvScheduleServices.visibility = View.GONE
                }
            }
        )
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
        _binding = DialogAddScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timePicker.setIs24HourView(true)
        changedCalendar.clear()
        binding.rvScheduleServices.adapter = adapter
        adapter.onUpdateItems(services)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onStart() {
        super.onStart()
        MainScope().launch(Dispatchers.IO) {

        }
        binding.root.setOnClickListener {
            dismissAllowingStateLoss()
        }

        val currentDate = Calendar.getInstance()
        binding.datePicker.init(
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            changedCalendar.set(Calendar.YEAR, year)
            changedCalendar.set(Calendar.MONTH, month)
            changedCalendar.set(Calendar.DAY_OF_MONTH, day)
        }
        binding.timePicker.setOnTimeChangedListener { _, hours, minutes ->
            changedCalendar.set(Calendar.HOUR_OF_DAY, hours)
            changedCalendar.set(Calendar.MINUTE, minutes)
        }
        binding.btnCreateSchedule.setOnClickListener {
            delegate.onAccept(
                changedService,
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(changedCalendar.time).toString()
            )
            dismissAllowingStateLoss()
        }
    }

    override fun onStop() {
        super.onStop()
        binding.root.setOnClickListener(null)
        binding.btnCreateSchedule.setOnClickListener(null)
    }
}