package com.example.beautyshop.presentation.admin.schedules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.SectionModel
import com.example.beautyshop.databinding.FragmentServiceBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter

class ScheduleFragment : Fragment() {

    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[ScheduleViewModel::class.java]
    }

    private val adapter: RenderAdapter<SectionModel> by lazy {
        RenderAdapter(
            RenderViewType.SectionsViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {
                    NavHostFragment.findNavController(this@ScheduleFragment)
                        .navigate(
                            ScheduleFragmentDirections.actionServicesFragmentToServicePageFragment(
                                position
                            ), NavOptions.Builder()
                                .setRestoreState(false)
                                .build()
                        )
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSections.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.onGetData().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }
    }
}