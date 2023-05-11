package com.example.beautyshop.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.beautyshop.databinding.FragmentRegistrationBinding
import com.example.beautyshop.presentation.auth.LoginActivity
import com.google.android.material.snackbar.Snackbar

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnRegistration.setOnClickListener {
            viewModel.onRegister(
                binding.etEmail.text.toString(),
                binding.etFirstName.text.toString(),
                binding.etSecondName.text.toString(),
                binding.etPassword.text.toString(),
                binding.etRepeatPassword.text.toString(),
            )
        }
        binding.loginNow.setOnClickListener {
            (requireActivity() as LoginActivity).onPopBack()
        }

        viewModel.onGetIsError().observe(viewLifecycleOwner) {
            binding.repeatPasswordLayout.error = it
        }

        viewModel.onGetIsLoad().observe(viewLifecycleOwner) {
            if (it) {
                binding.btnRegistration.visibility = View.GONE
                binding.contentLoad.visibility = View.VISIBLE
            } else {
                binding.btnRegistration.visibility = View.VISIBLE
                binding.contentLoad.visibility = View.GONE
            }
        }

        viewModel.onGetIsSuccess().observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            (requireActivity() as LoginActivity).onPopBack()
        }
    }
}