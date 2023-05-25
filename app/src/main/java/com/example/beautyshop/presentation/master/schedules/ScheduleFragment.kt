package com.example.beautyshop.presentation.master.schedules

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.ScheduleModel
import com.example.beautyshop.databinding.FragmentScheduleBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.master.schedules.add_schedule.AddScheduleDialog
import com.example.beautyshop.presentation.root.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.util.*

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[ScheduleViewModel::class.java]
    }
    private val adapter: RenderAdapter<ScheduleModel> by lazy {
        RenderAdapter(
            RenderViewType.MasterSchedulesViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {

                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAppointments.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        binding.addSchedule.setOnClickListener {
            if (!viewModel.onGetServices().value.isNullOrEmpty()) {
                (requireActivity() as MainActivity).onShowDialogFragment(
                    AddScheduleDialog(
                        viewModel.onGetServices().value ?: mutableListOf(),
                        object : AddScheduleDialog.IAddScheduleDialog {
                            override fun onAccept(serviceId: Int, dateTime: String) {
                                viewModel.onCreateSchedule(serviceId, dateTime)
                            }
                        })
                )
            } else {
                Snackbar.make(
                    binding.root,
                    "Мастер не прикреплен к секции услуг",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.onGetIsError().observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.onGetData().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }
        viewModel.onLoadData()
    }
}