package com.example.beautyshop.presentation.master.works

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.WorkModel
import com.example.beautyshop.data.models.WorkOfMasterModel
import com.example.beautyshop.databinding.FragmentMasterWorkBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.master.works.add_work.AddWorkDialog
import com.example.beautyshop.presentation.root.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MasterWorkFragment : Fragment() {

    private var _binding: FragmentMasterWorkBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[MasterWorkViewModel::class.java]
    }
    private val adapter: RenderAdapter<WorkModel> by lazy {
        RenderAdapter(
            RenderViewType.WorksViewType.viewType,
            object : RenderAdapter.IItemClickListener {
                override fun onClick(position: Int) {
                    viewModel.onRemoveWork(position)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMasterWorkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvWorks.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        viewModel.onGetIsError().observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.onLoadData()
        viewModel.onGetData().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }

        binding.addWorks.setOnClickListener {
            (requireActivity() as MainActivity).onShowDialogFragment(AddWorkDialog(object :
                AddWorkDialog.IAddWorkDialog {
                override fun onAccept(path: String, description: String) {
                    viewModel.onAddWork(path, description)
                }
            }))
        }
    }
}