package com.cellfishpool.calendarpoc.calendarviews.weekview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cellfishpool.calendarpoc.R
import com.cellfishpool.calendarpoc.calendarutils.CalendarUtils
import com.cellfishpool.calendarpoc.calendarutils.RecyclerCalendarBaseAdapter
import com.cellfishpool.calendarpoc.calendarutils.models.RecyclerCalendarConfiguration
import com.cellfishpool.calendarpoc.calendarutils.models.RecyclerCalenderViewItem
import com.cellfishpool.calendarpoc.databinding.ItemCalendarHorizontalBinding
import java.util.Calendar
import java.util.Date

class HorizontalRecyclerCalendarAdapter(
    startDate: Date,
    endDate: Date,
    private val configuration: RecyclerCalendarConfiguration,
    private var selectedDate: Date,
    private val dateSelectListener: OnDateSelected
) : RecyclerCalendarBaseAdapter(startDate, endDate, configuration) {
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        calendarItem: RecyclerCalenderViewItem
    ) {
        (holder as MonthCalendarViewHolder).bind(calendarItem)
    }

    inner class MonthCalendarViewHolder(private val binding: ItemCalendarHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(calendarItem: RecyclerCalenderViewItem) {
            itemView.visibility = View.VISIBLE
            itemView.setOnClickListener(null)
            itemView.background = null

            with(binding) {
                tvDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.grey_100))
                tvDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))

                when {
                    calendarItem.isHeader -> {
                        val selectedCalendar = Calendar.getInstance()
                        selectedCalendar.time = calendarItem.date

                        val month: String = CalendarUtils.dateStringFromFormat(
                            locale = configuration.calendarLocale,
                            date = selectedCalendar.time,
                            format = CalendarUtils.DISPLAY_MONTH_FORMAT
                        ) ?: ""
                        val year = selectedCalendar[Calendar.YEAR].toLong()

                        tvDay.text = year.toString()
                        tvDate.text = month

                        itemView.setOnClickListener(null)
                    }
                    calendarItem.isEmpty -> {
                        itemView.visibility = View.GONE
                        tvDay.text = ""
                        tvDate.text = ""
                    }
                    else -> {
                        val calendarDate = Calendar.getInstance()
                        calendarDate.time = calendarItem.date

                        val stringCalendarTimeFormat: String =
                            CalendarUtils.dateStringFromFormat(
                                locale = configuration.calendarLocale,
                                date = calendarItem.date,
                                format = CalendarUtils.DB_DATE_FORMAT
                            )
                                ?: ""
                        val stringSelectedTimeFormat: String =
                            CalendarUtils.dateStringFromFormat(
                                locale = configuration.calendarLocale,
                                date = selectedDate,
                                format = CalendarUtils.DB_DATE_FORMAT
                            ) ?: ""

                        if (stringCalendarTimeFormat == stringSelectedTimeFormat) {
                            tvDate.setBackgroundResource(R.color.purple_200)
                            tvDate.setTextColor(
                                ContextCompat.getColor(
                                    itemView.context,
                                    R.color.white
                                )
                            )
                        }

                        val day: String = CalendarUtils.dateStringFromFormat(
                            locale = configuration.calendarLocale,
                            date = calendarDate.time,
                            format = CalendarUtils.DISPLAY_WEEK_DAY_FORMAT
                        ) ?: ""

                        tvDay.text = day
                        tvDate.text =
                            CalendarUtils.dateStringFromFormat(
                                locale = configuration.calendarLocale,
                                date = calendarDate.time,
                                format = CalendarUtils.DISPLAY_DATE_FORMAT
                            ) ?: ""
                        itemView.setOnClickListener {
                            tvDate.setBackgroundResource(R.drawable.layout_selected_date)
                            dateSelectListener.onDateSelected(calendarItem.date)
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCalendarHorizontalBinding.inflate(layoutInflater, parent, false)
        return MonthCalendarViewHolder(binding)
    }

    interface OnDateSelected {
        fun onDateSelected(date: Date)
    }
}