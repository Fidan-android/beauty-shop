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
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.beautyshop.R
import com.example.beautyshop.conventions.RenderViewType
import com.example.beautyshop.data.models.WorkModel
import com.example.beautyshop.data.models.WorkOfMasterModel
import com.example.beautyshop.databinding.FragmentWorkBinding
import com.example.beautyshop.presentation.adapters.RenderAdapter
import java.util.*

class WorkFragment : Fragment() {
    private var _binding: FragmentWorkBinding? = null
    private val binding get() = _binding!!
    private val args: WorkFragmentArgs by navArgs()
    private val viewModel by lazy {
        ViewModelProvider(this)[WorkViewModel::class.java]
    }
    private val adapter: RenderAdapter<WorkModel> by lazy {
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
        binding.goBack.setOnClickListener {
            NavHostFragment.findNavController(this@WorkFragment).popBackStack()
        }
        viewModel.onGetData().observe(viewLifecycleOwner) {
            adapter.onUpdateItems(it)
        }

        viewModel.onGetMasterData().observe(viewLifecycleOwner) {
            binding.masterName.text = it.masterName
            Glide
                .with(requireContext())
                .load(
                    GlideUrl(
                        "http://c95130nt.beget.tech/api/v1/img/" + it.masterAvatar,
                        LazyHeaders.Builder()
                            .addHeader("User-Agent", "Mozilla/5.0")
                            .build()
                    )
                )
                .centerCrop()
                .error(R.drawable.sample_avatar)
                .into(binding.masterAvatar)
        }
    }
}