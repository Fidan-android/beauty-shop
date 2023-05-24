package com.example.beautyshop.presentation.master.works

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.AppointmentModel
import com.example.beautyshop.databinding.FragmentWorkBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.google.android.material.snackbar.Snackbar
import java.util.*

class WorkFragment : Fragment() {

    private var _binding: FragmentWorkBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[WorkViewModel::class.java]
    }
    private val adapter: RenderAdapter<AppointmentModel> by lazy {
        RenderAdapter(
            RenderViewType.MasterAppointmentsViewType.viewType,
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

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        viewModel.onGetIsError().observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.onLoadData()
    }
}