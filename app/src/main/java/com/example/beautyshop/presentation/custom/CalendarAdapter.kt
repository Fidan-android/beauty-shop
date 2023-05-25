package com.example.beautyshop.presentation.custom

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatTextView
import com.example.beautyshop.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    private val changedPosition: Int,
    context: Context,
    days: MutableList<String>,
    private val delegate: ICalendarAdapterListener
) :
    ArrayAdapter<String>(context, 0, days) {

    interface ICalendarAdapterListener {
        fun onClick(position: Int, move: Boolean)
    }

    @SuppressLint("SimpleDateFormat")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.calendar_day_cell, parent, false)
        }

        view!!.findViewById<AppCompatTextView>(R.id.textDay).apply {
            text = getItem(position).toString().substringBefore("-")
            if (!getItem(position).isNullOrEmpty()) {
                val calendar = Calendar.getInstance().apply {
                    time = SimpleDateFormat("yyyy-M-d").parse(getItem(position)!!)!!
                }
                text = SimpleDateFormat("d-M").format(calendar.time).toString().substringBefore("-")
                setOnClickListener {
                    isActivated = !isActivated
                    delegate.onClick(position, isActivated)
                }

                isEnabled = calendar.get(Calendar.MONTH) >= Calendar.getInstance()
                    .get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH) > Calendar.getInstance()
                    .get(Calendar.DAY_OF_MONTH)

                isActivated = changedPosition == position
            }
        }
        return view
    }
}