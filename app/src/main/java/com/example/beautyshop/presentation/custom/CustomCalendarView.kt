package com.example.beautyshop.presentation.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.beautyshop.R
import com.example.beautyshop.databinding.CustomCalendarViewBinding
import com.example.beautyshop.helper.compareFirstDayOfWeek
import java.text.SimpleDateFormat
import java.util.*

interface ICustomCalendarView {
    fun onConfigure(
        month: Int,
        delegate: CustomCalendarView.ICustomCalendarListener
    )
}

class CustomCalendarView : LinearLayout, ICustomCalendarView {

    interface ICustomCalendarListener {
        fun addItem(date: Date)
        fun removeItem(date: Date)
    }

    private val pDays: MutableList<Pair<String, Date?>> = mutableListOf()

    var binding: CustomCalendarViewBinding? = null

    constructor(context: Context) : super(context) {
        if (!isInEditMode)
            init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        if (!isInEditMode) {
            init(context)
            parseAttributes(context, attributeSet)
        }
    }

    constructor(context: Context, attributeSet: AttributeSet, styleAttr: Int) : super(
        context,
        attributeSet,
        styleAttr
    ) {
        if (!isInEditMode) {
            init(context)
            parseAttributes(context, attributeSet)
        }
    }

    private fun init(context: Context) {
        val inputView =
            LayoutInflater.from(context).inflate(R.layout.custom_calendar_view, this, true)
        binding = CustomCalendarViewBinding.bind(inputView.findViewById(R.id.inputRoot))
    }

    private fun parseAttributes(context: Context, attributeSet: AttributeSet) {
        val attributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.CustomCalendarView)
        attributes.recycle()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onConfigure(
        month: Int,
        delegate: ICustomCalendarListener
    ) {
        val calendar = Calendar.getInstance().apply {
            clear()
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
        }

        for (i in 1..calendar.compareFirstDayOfWeek()) {
            pDays.add("" to null)
        }

        var numberOfDay = 1
        while (numberOfDay <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            calendar.set(Calendar.DAY_OF_MONTH, numberOfDay)
            pDays.add(SimpleDateFormat("yyyy-M-d").format(calendar.time) to calendar.time)
            numberOfDay++
        }

        binding?.titleMonth?.text = convertStringMonthToCalendar(
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR)
        )
        binding?.calendarView?.adapter =
            CalendarAdapter(
                context,
                pDays.map { item -> item.first }.toMutableList(),
                object : CalendarAdapter.ICalendarAdapterListener {
                    override fun onClick(position: Int, move: Boolean) {
                        if (move) {
                            delegate.addItem(pDays[position].second!!)

                        } else {
                            delegate.removeItem(pDays[position].second!!)
                        }
                    }
                }
            )
    }

    @SuppressLint("SimpleDateFormat")
    fun convertStringMonthToCalendar(month: Int, year: Int): String {
        return when (month) {
            1 -> "Январь $year"
            2 -> "Февраль $year"
            3 -> "Март $year"
            4 -> "Апрель $year"
            5 -> "Май $year"
            6 -> "Июнь $year"
            7 -> "Июль $year"
            8 -> "Август $year"
            9 -> "Сентябрь $year"
            10 -> "Октябрь $year"
            11 -> "Ноябрь $year"
            12 -> "Декабрь $year"
            else -> ""
        }
    }
}