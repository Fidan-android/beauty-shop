package com.example.beautyshop.presentation.admin.workers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.App
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.databinding.FragmentAdminWorkerBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.dialogs.ITextDialogWithYesNo
import com.example.beautyshop.presentation.dialogs.TextDialogWithYesNo
import com.example.beautyshop.presentation.master.schedules.add_schedule.AddScheduleDialog
import com.example.beautyshop.presentation.root.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

class AdminWorkerFragment : Fragment() {

    private var _binding: FragmentAdminWorkerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[AdminWorkerViewModel::class.java]
    }

    private val adapter: RenderAdapter<ProfileModel> by lazy {
        RenderAdapter(
            RenderViewType.WorkersViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {
                    viewModel.onLoadServices(position)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminWorkerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvWorkers.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.onGetData().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }
        viewModel.onGetServices().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                (requireActivity() as MainActivity).onShowDialogFragment(
                    AddScheduleDialog(
                        viewModel.onGetServices().value ?: mutableListOf(),
                        object : AddScheduleDialog.IAddScheduleDialog {
                            @SuppressLint("SimpleDateFormat")
                            override fun onAccept(serviceId: Int, dateTime: String) {
                                (requireActivity() as MainActivity).onShowDialogFragment(
                                    TextDialogWithYesNo(
                                        App.appContext.getString(
                                            R.string.time_schedule,
                                            SimpleDateFormat("HH:mm").format(
                                                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                                                    dateTime
                                                )!!
                                            )
                                        ),
                                        object : ITextDialogWithYesNo {
                                            override fun onAccept() {
                                                viewModel.onCreateSchedule(serviceId, dateTime)
                                            }
                                        }
                                    )
                                )
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
    }
}