package com.example.beautyshop.presentation.admin.schedules.schedule_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.ScheduleModel
import com.example.beautyshop.databinding.FragmentAdminServicePageBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.custom.CustomCalendarView
import com.example.beautyshop.presentation.dialogs.ITextDialogWithYesNo
import com.example.beautyshop.presentation.dialogs.TextDialogWithYesNo
import com.example.beautyshop.presentation.root.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class SchedulePageFragment : Fragment() {
    private var _binding: FragmentAdminServicePageBinding? = null
    private val binding get() = _binding!!
    private val args: SchedulePageFragmentArgs by navArgs()
    private val viewModel by lazy {
        ViewModelProvider(this)[SchedulePageViewModel::class.java]
    }
    private val adapter: RenderAdapter<ScheduleModel> by lazy {
        RenderAdapter(
            RenderViewType.ScheduleTimesViewType.viewType,
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
        _binding = FragmentAdminServicePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvScheduleTimes.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        binding.goBack.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        viewModel.onGetProfileData().observe(viewLifecycleOwner) {
            binding.userName.text = it.firstName + " " + it.name
            Glide
                .with(requireContext())
                .load(
                    GlideUrl(
                        "http://c95130nt.beget.tech/api/v1/img/" + it.image,
                        LazyHeaders.Builder()
                            .addHeader("User-Agent", "Mozilla/5.0")
                            .build()
                    )
                )
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .error(R.drawable.sample_avatar)
                .into(binding.userAvatar)
        }
        viewModel.onGetData().observe(viewLifecycleOwner) {
            val section =
                it.first { section -> section.services.firstOrNull { service -> service.id == args.serviceId } != null }
            val service = section.services.first { service -> service.id == args.serviceId }

            binding.serviceName.text = service.serviceName
            binding.servicePrice.text = service.price
            binding.serviceHours.text = "${service.time} ${service.measurement}."
            onConfigureCalendar(Calendar.getInstance().get(Calendar.MONTH))
        }
        viewModel.onGetSchedules().observe(viewLifecycleOwner) {
            val schedule = it.firstOrNull { model -> model.serviceId == args.serviceId }
            if (schedule != null) {
                binding.masterName.text = schedule.master
                binding.masterName.isSelected = true
                Glide
                    .with(requireContext())
                    .load(
                        GlideUrl(
                            "http://c95130nt.beget.tech/api/v1/img/" + schedule.masterAvatar,
                            LazyHeaders.Builder()
                                .addHeader("User-Agent", "Mozilla/5.0")
                                .build()
                        )
                    )
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .error(R.drawable.sample_avatar)
                    .into(binding.masterAvatar)
            } else {
                binding.masterName.visibility = View.GONE
                binding.masterAvatar.visibility = View.GONE
            }
        }
        viewModel.onGetIsSuccess().observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        binding.nextMonth.setOnClickListener {
            binding.currentMonth.visibility = View.VISIBLE
            binding.nextMonth.visibility = View.GONE
            clearForm()
            binding.currentCalendarView.onReset()
            onConfigureCalendar(Calendar.getInstance().get(Calendar.MONTH) + 1)
        }

        binding.currentMonth.setOnClickListener {
            binding.currentMonth.visibility = View.GONE
            binding.nextMonth.visibility = View.VISIBLE
            clearForm()
            binding.currentCalendarView.onReset()
            onConfigureCalendar(Calendar.getInstance().get(Calendar.MONTH))
        }
    }

    private fun onConfigureCalendar(month: Int) {
        binding.currentCalendarView.onConfigure(
            month,
            object : CustomCalendarView.ICustomCalendarListener {
                @SuppressLint("SimpleDateFormat")
                override fun addItem(date: Date) {
                    binding.changedDate.text =
                        SimpleDateFormat("EEEE, dd MMMM", Locale("RU")).format(date)
                    adapter.onUpdateItems(viewModel.onGetScheduleTimes(args.serviceId, date))
                }

                override fun removeItem(date: Date) {
                    clearForm()
                }
            }
        )
    }

    private fun clearForm() {
        binding.changedDate.text = ""
        adapter.onUpdateItems(mutableListOf())
    }
}