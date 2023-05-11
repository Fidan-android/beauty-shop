package com.example.beautyshop.presentation.user.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.conventions.SharedKeys
import com.example.beautyshop.databinding.FragmentProfileBinding
import com.example.beautyshop.helper.removeShared
import com.example.beautyshop.presentation.auth.LoginActivity
import com.example.beautyshop.presentation.auth.LoginViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.logOut.setOnClickListener {
            requireContext().removeShared(SharedKeys.AccessToken)

            requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}