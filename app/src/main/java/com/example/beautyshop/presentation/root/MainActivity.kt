package com.example.beautyshop.presentation.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.beautyshop.R
import com.example.beautyshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IMainActivity {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var selectedItem = -1
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.onConfigureRole()
        viewModel.onGetData().observe(this) {
            when (it) {
                0 -> onConfigureUser()
                1 -> onConfigureMaster()
                2 -> onConfigureAdmin()
            }
            binding.bottomMenu.visibility = View.VISIBLE
        }
    }

    override fun onConfigureUser() {
        binding.bottomMenu.menu.clear()
        binding.bottomMenu.inflateMenu(R.menu.user_bottom_menu)
        navHostFragment.navController.graph =
            navHostFragment.navController.navInflater.inflate(R.navigation.user_nav_graph)
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

    override fun onConfigureMaster() {
        binding.bottomMenu.menu.clear()
        binding.bottomMenu.inflateMenu(R.menu.master_bootom_menu)
        navHostFragment.navController.graph =
            navHostFragment.navController.navInflater.inflate(R.navigation.master_nav_graph)
        NavigationUI.setupWithNavController(binding.bottomMenu, navHostFragment.navController)
        binding.bottomMenu.setOnItemSelectedListener { item ->
            if (selectedItem == -1 || item.itemId != selectedItem) {
                when (item.itemId) {
                    R.id.masterWorkFragment -> {
                        navHostFragment.navController.navigate(
                            R.id.masterWorkFragment,
                            Bundle(),
                            NavOptions.Builder()
                                .setPopUpTo(R.id.masterWorkFragment, true)
                                .build()
                        )
                    }
                    R.id.masterScheduleFragment -> {
                        navHostFragment.navController.navigate(
                            R.id.masterScheduleFragment,
                            Bundle(),
                            NavOptions.Builder()
                                .setPopUpTo(R.id.masterScheduleFragment, true)
                                .build()
                        )
                    }
                    R.id.masterProfileFragment -> {
                        navHostFragment.navController.navigate(
                            R.id.masterProfileFragment,
                            Bundle(),
                            NavOptions.Builder()
                                .setPopUpTo(R.id.masterProfileFragment, true)
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

    override fun onConfigureAdmin() {
        binding.bottomMenu.menu.clear()
        binding.bottomMenu.inflateMenu(R.menu.admin_bootom_menu)
        navHostFragment.navController.graph =
            navHostFragment.navController.navInflater.inflate(R.navigation.admin_nav_graph)
        NavigationUI.setupWithNavController(binding.bottomMenu, navHostFragment.navController)
        binding.bottomMenu.setOnItemSelectedListener { item ->
            if (selectedItem == -1 || item.itemId != selectedItem) {
                when (item.itemId) {
                    R.id.adminServiceFragment -> {
                        navHostFragment.navController.navigate(R.id.adminServiceFragment)
                    }
                    R.id.adminWorkerFragment -> {
                        navHostFragment.navController.navigate(R.id.adminWorkerFragment)
                    }
                    R.id.adminUserFragment -> {
                        navHostFragment.navController.navigate(R.id.adminUserFragment)
                    }
                    R.id.adminScheduleFragment -> {
                        navHostFragment.navController.navigate(R.id.adminScheduleFragment)
                    }
                    R.id.adminProfileFragment -> {
                        navHostFragment.navController.navigate(R.id.adminProfileFragment)
                    }
                }
                selectedItem = item.itemId
                return@setOnItemSelectedListener true
            } else {
                return@setOnItemSelectedListener false
            }
        }
    }

    override fun onShowDialogFragment(fragment: DialogFragment) {
        fragment.show(supportFragmentManager, fragment::class.java.name)
    }
}