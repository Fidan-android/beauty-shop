package com.example.beautyshop.presentation.user.workers.page

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
import com.example.beautyshop.data.models.WorkOfMasterModel
import com.example.beautyshop.databinding.FragmentWorkBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.custom.CustomCalendarView
import com.example.beautyshop.presentation.root.MainActivity
import com.example.beautyshop.presentation.user.service_page.create_appointment.AddAppointmentDialog
import java.text.SimpleDateFormat
import java.util.*

class WorkFragment : Fragment() {
    private var _binding: FragmentWorkBinding? = null
    private val binding get() = _binding!!
    private val args: WorkFragmentArgs by navArgs()
    private val viewModel by lazy {
        ViewModelProvider(this)[WorkViewModel::class.java]
    }
    private val adapter: RenderAdapter<WorkOfMasterModel> by lazy {
        RenderAdapter(
            RenderViewType.WorkOfMasterViewType.viewType,
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
        _binding = FragmentWorkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvWorks.adapter = adapter
        viewModel.onLoadData(args.userId)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        viewModel.onGetData().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }
    }
}