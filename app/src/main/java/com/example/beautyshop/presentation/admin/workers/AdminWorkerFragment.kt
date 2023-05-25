package com.example.beautyshop.presentation.admin.workers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.ProfileModel
import com.example.beautyshop.databinding.FragmentAdminWorkerBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.admin.workers.dialog.AddWorkerDialog
import com.example.beautyshop.presentation.root.MainActivity

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
                    (requireActivity() as MainActivity).onShowDialogFragment(
                        AddWorkerDialog(object : AddWorkerDialog.IAddWorkerDialog {
                            override fun onAccept() {
                                viewModel.onMoveWorker(position)
                            }
                        })
                    )
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
        viewModel.onLoadData()
    }

    override fun onStart() {
        super.onStart()

        viewModel.onGetData().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }
    }
}