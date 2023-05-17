package com.example.beautyshop.presentation.root

import androidx.lifecycle.MutableLiveData

interface IMainViewModel {
    fun onGetData(): MutableLiveData<Int>
    fun onConfigureRole()
}