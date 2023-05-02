package com.example.beautyshop.presentation.adapters

interface IRenderAdapter<T> {
    fun onUpdateItems(list: MutableList<T>)
}