package com.example.beautyshop.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beautyshop.databinding.FragmentRegistrationBinding
import com.example.beautyshop.presentation.auth.LoginActivity

class RegistrationFragment: Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

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

        binding.loginNow.setOnClickListener {
            (requireActivity() as LoginActivity).onPopBack()
        }
    }
}