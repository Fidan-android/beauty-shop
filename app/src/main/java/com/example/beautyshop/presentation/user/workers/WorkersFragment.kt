package com.example.beautyshop.presentation.user.workers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.WorkersModel
import com.example.beautyshop.databinding.FragmentLoginBinding
import com.example.beautyshop.databinding.FragmentWorkersBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.auth.LoginViewModel

class WorkersFragment: Fragment() {

    private var _binding: FragmentWorkersBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private val adapter: RenderAdapter<WorkersModel> by lazy {
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
        _binding = FragmentWorkersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvWorkers.adapter = adapter
        adapter.onUpdateItems(
            mutableListOf(
                WorkersModel(0, "", "", "", ""),
                WorkersModel(1, "", "", "", ""),
                WorkersModel(2, "", "", "", ""),
                WorkersModel(3, "", "", "", ""),
                WorkersModel(4, "", "", "", "")
            )
        )
    }
}