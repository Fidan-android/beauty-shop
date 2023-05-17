package com.example.beautyshop.presentation.root

import androidx.fragment.app.DialogFragment

interface IMainActivity {
    fun onConfigureUser()
    fun onConfigureMaster()
    fun onConfigureAdmin()
    fun onShowDialogFragment(fragment: DialogFragment)
}