package com.example.beautyshop.presentation.auth

import androidx.fragment.app.Fragment

interface ILoginActivity {
    fun onReplaceFragment(pFragment: Fragment)
    fun onPushFragment(pFragment: Fragment)
    fun onPopBack()
}