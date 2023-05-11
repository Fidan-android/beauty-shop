package com.example.beautyshop.presentation.user.services

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
import com.example.beautyshop.data.models.ServiceModel
import com.example.beautyshop.data.models.WorkerModel
import com.example.beautyshop.databinding.FragmentServiceBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter

class ServiceFragment : Fragment() {

    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[ServiceViewModel::class.java]
    }

    private val adapter: RenderAdapter<SectionModel> by lazy {
        RenderAdapter(
            RenderViewType.SectionsViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {
                    NavHostFragment.findNavController(this@ServiceFragment)
                        .navigate(
                            ServiceFragmentDirections.actionServicesFragmentToServicePageFragment(
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
        adapter.onUpdateItems(
            mutableListOf(
                SectionModel(
                    0, "Ресницы", mutableListOf(
                        ServiceModel(
                            1,
                            "Увеличение объема",
                            WorkerModel(0, "", "", "", ""),
                            299f,
                            2
                        ),
                        ServiceModel(
                            2,
                            "Увеличение объема",
                            WorkerModel(0, "", "", "", ""),
                            299f,
                            2
                        ),
                        ServiceModel(
                            3,
                            "Увеличение объема",
                            WorkerModel(0, "", "", "", ""),
                            299f,
                            2
                        ),
                    )
                ),
                SectionModel(
                    1, "Волосы", mutableListOf(
                        ServiceModel(
                            1,
                            "Увеличение объема",
                            WorkerModel(0, "", "", "", ""),
                            299f,
                            2
                        ),
                        ServiceModel(
                            2,
                            "Увеличение объема",
                            WorkerModel(0, "", "", "", ""),
                            299f,
                            2
                        ),
                        ServiceModel(
                            3,
                            "Увеличение объема",
                            WorkerModel(0, "", "", "", ""),
                            299f,
                            2
                        ),
                    )
                ),
                SectionModel(
                    2, "Губы", mutableListOf(
                        ServiceModel(
                            1,
                            "Увеличение объема",
                            WorkerModel(0, "", "", "", ""),
                            299f,
                            2
                        ),
                        ServiceModel(
                            2,
                            "Увеличение объема",
                            WorkerModel(0, "", "", "", ""),
                            299f,
                            2
                        ),
                        ServiceModel(
                            3,
                            "Увеличение объема",
                            WorkerModel(0, "", "", "", ""),
                            299f,
                            2
                        ),
                    )
                ),
            )
        )
    }
}