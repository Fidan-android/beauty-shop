package com.example.beautyshop.presentation.user.workers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.WorkerModel
import com.example.beautyshop.databinding.FragmentWorkerBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.auth.LoginViewModel

class WorkerFragment : Fragment() {

    private var _binding: FragmentWorkerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private val adapter: RenderAdapter<WorkerModel> by lazy {
        RenderAdapter(
            RenderViewType.WorkersViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {

                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvWorkers.adapter = adapter
        adapter.onUpdateItems(
            mutableListOf(
                WorkerModel(0, "", "", "", ""),
                WorkerModel(1, "", "", "", ""),
                WorkerModel(2, "", "", "", ""),
                WorkerModel(3, "", "", "", ""),
                WorkerModel(4, "", "", "", "")
            )
        )
    }
}