package com.example.beautyshop.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.presentation.root.MainActivity
import com.example.beautyshop.conventions.SharedKeys
import com.example.beautyshop.data.IInternetConnected
import com.example.beautyshop.data.api.ApiManager
import com.example.beautyshop.databinding.FragmentLoginBinding
import com.example.beautyshop.helper.saveShared
import com.example.beautyshop.presentation.auth.register.RegistrationFragment

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        ApiManager.setConnectCallback(requireContext(), object : IInternetConnected {
            override fun onConnect() {

            }

            override fun onLost() {

            }

        })
        super.onStart()

        viewModel.onGetIsLoad().observe(viewLifecycleOwner) {
            if (it) {
                binding.btnAuthorize.visibility = View.GONE
                binding.contentLoad.visibility = View.VISIBLE
            } else {
                binding.btnAuthorize.visibility = View.VISIBLE
                binding.contentLoad.visibility = View.GONE
            }
        }

        viewModel.onGetIsSuccess().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                requireContext().saveShared(SharedKeys.AccessToken, it)
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
        viewModel.onGetIsError().observe(viewLifecycleOwner) {
            binding.passwordLayout.error = it
        }

        binding.btnAuthorize.setOnClickListener {
            viewModel.onLogin(binding.etLogin.text.toString(), binding.etPassword.text.toString())
        }

        binding.registerNow.setOnClickListener {
            (requireActivity() as LoginActivity).onPushFragment(RegistrationFragment())
        }
    }
}