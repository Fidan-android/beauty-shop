package com.example.beautyshop.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.beautyshop.data.models.WorkerModel

class CustomDiffUtil<T>(
    private val viewType: Int,
    private val oldProductList: List<T>,
    private val newProductList: List<T>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when (viewType) {
            0 -> (oldProductList[oldItemPosition] as WorkerModel).id !=
                    (newProductList[newItemPosition] as WorkerModel).id
            else -> true
        }
    }

    override fun getOldListSize(): Int = oldProductList.size

    override fun getNewListSize(): Int = newProductList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when (viewType) {
            0 ->
                (oldProductList[oldItemPosition] as WorkerModel).id !=
                        (newProductList[newItemPosition] as WorkerModel).id
            else -> true
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}