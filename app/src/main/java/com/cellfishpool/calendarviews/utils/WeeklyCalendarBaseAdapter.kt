package com.cellfishpool.calendarviews.utils

import android.os.Handler
import android.os.HandlerThread
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*

abstract class WeeklyCalendarBaseAdapter(
    private val startDate: Date,
    private val endDate: Date
) : RecyclerView.Adapter<ViewHolder>() {

    var mHandlerThread: MyHandlerThread =
        MyHandlerThread(
            WeeklyCalendarBaseAdapter::class.java.simpleName
        )
    private val handler = Handler()

    private val _calendarItemList = arrayListOf<DateHolder>()

    fun getItem(position: Int) = _calendarItemList[position]

    init {
        val startCalendar = Calendar.getInstance(Locale.getDefault())
        val endCalendar = Calendar.getInstance(Locale.getDefault())
        startCalendar.time = startDate
        startCalendar.getNearestSunday()

        endCalendar.time = endDate
        endCalendar[Calendar.DAY_OF_MONTH] = endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        endCalendar[Calendar.HOUR_OF_DAY] = 0
        endCalendar[Calendar.MINUTE] = 0
        endCalendar[Calendar.SECOND] = 0
        endCalendar[Calendar.MILLISECOND] = 0

        val runnable = Runnable {
            while (startCalendar.time.before(
                    endCalendar.time
                ) || startCalendar.time == endCalendar.time
            ) {
                val calendarDateItem = startCalendar.time
                _calendarItemList.add(DateHolder(calendarDateItem, true))
                startCalendar.add(Calendar.DATE, 7)
            }
            handler.post {
                notifyDataSetChanged()
            }
        }

        mHandlerThread.start()
        mHandlerThread.prepareHandler()
        mHandlerThread.postTask(runnable)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        onBindViewHolder(
            holder = holder,
            position = position,
            calendarItem = _calendarItemList[position]
        )
    }

    abstract fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        calendarItem: DateHolder
    )

    override fun getItemCount(): Int = _calendarItemList.size

    class MyHandlerThread constructor(name: String) :
        HandlerThread(name) {
        private var handler: Handler? = null
        fun postTask(task: Runnable) {
            handler!!.post(task)
        }

        fun prepareHandler() {
            handler = Handler(looper)
        }
    }

    companion object {
        data class DateHolder(
            val date: Date,
            val isEnabled: Boolean
        )
    }
}
