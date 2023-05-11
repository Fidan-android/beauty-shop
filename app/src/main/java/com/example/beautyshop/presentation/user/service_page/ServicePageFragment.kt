package com.example.beautyshop.presentation.user.service_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.beautyshop.databinding.FragmentServicePageBinding
import com.example.beautyshop.presentation.custom.CustomCalendarView
import java.text.SimpleDateFormat
import java.util.*

class ServicePageFragment : Fragment() {
    private var _binding: FragmentServicePageBinding? = null
    private val binding get() = _binding!!
    private val args: ServicePageFragmentArgs by navArgs()
    private val viewModel by lazy {
        ViewModelProvider(this)[ServicePageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarView.onConfigure(
            Calendar.getInstance().get(Calendar.MONTH),
            object : CustomCalendarView.ICustomCalendarListener {
                @SuppressLint("SimpleDateFormat")
                override fun addItem(date: Date) {
                    binding.changedDate.text =
                        SimpleDateFormat("EEEE, dd MMMM", Locale("RU")).format(date)
                }

                override fun removeItem(date: Date) {

                }
            })
    }

    override fun onStart() {
        super.onStart()
        binding.goBack.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }
}