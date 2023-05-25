package com.example.beautyshop.presentation.master.works

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.AppointmentModel
import com.example.beautyshop.data.models.WorkOfMasterModel
import com.example.beautyshop.databinding.FragmentMasterWorkBinding
import com.example.beautyshop.helper.copyInputStreamToFile
import com.example.beautyshop.helper.toIso
import com.example.beautyshop.presentation.adapters.RenderAdapter
import com.example.beautyshop.presentation.master.works.add_work.AddWorkDialog
import com.example.beautyshop.presentation.root.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.InputStream
import java.util.*

class MasterWorkFragment : Fragment() {

    private var _binding: FragmentMasterWorkBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[MasterWorkViewModel::class.java]
    }
    private val adapter: RenderAdapter<WorkOfMasterModel> by lazy {
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
            (requireActivity() as MainActivity).onShowDialogFragment(AddWorkDialog(object : AddWorkDialog.IAddWorkDialog {
                override fun onAccept(path: String, description: String) {
                    viewModel.onAddWork(path, description)
                }
            }))
        }
    }
}