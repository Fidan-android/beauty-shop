package com.example.beautyshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.beautyshop.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var selectedItem = -1
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        navHostFragment.navController.graph =
            navHostFragment.navController.navInflater.inflate(R.navigation.nav_graph)
        NavigationUI.setupWithNavController(binding.bottomMenu, navHostFragment.navController)
        binding.bottomMenu.setOnItemSelectedListener { item ->
            if (selectedItem == -1 || item.itemId != selectedItem) {
                when (item.itemId) {
                    R.id.homeFragment -> {
                        navHostFragment.navController.navigate(
                            R.id.homeFragment,
                            Bundle(),
                            NavOptions.Builder()
                                .setPopUpTo(R.id.homeFragment, true)
                                .build()
                        )
                    }
                    R.id.workersFragment -> {
                        navHostFragment.navController.navigate(
                            R.id.workersFragment,
                            Bundle(),
                            NavOptions.Builder()
                                .setPopUpTo(R.id.workersFragment, true)
                                .build()
                        )
                    }
                    R.id.servicesFragment -> {
                        navHostFragment.navController.navigate(
                            R.id.servicesFragment,
                            Bundle(),
                            NavOptions.Builder()
                                .setPopUpTo(R.id.servicesFragment, true)
                                .build()
                        )
                    }
                    R.id.profileFragment -> {
                        navHostFragment.navController.navigate(
                            R.id.profileFragment,
                            Bundle(),
                            NavOptions.Builder()
                                .setPopUpTo(R.id.profileFragment, true)
                                .build()
                        )
                    }
                }
                selectedItem = item.itemId
                return@setOnItemSelectedListener true
            } else {
                return@setOnItemSelectedListener false
            }
        }
    }
}