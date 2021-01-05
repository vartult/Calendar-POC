package com.cellfishpool.calendarviews.weeklyview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cellfishpool.calendarviews.R
import com.cellfishpool.calendarviews.databinding.ActivityWeeklyViewBinding
import com.cellfishpool.calendarviews.utils.*
import java.util.*

/*
* How Calendar is formed
* 1. Start Date & End Date (4 Weeks backward from today & 8 weeks ahead)
* 2. Find the Nearest Sunday (Weekly View Days are fixed)
* 3. Store all the starting date of the week (sundays) in the array
* 4. Pass each element from array to binding
* 5. set dates for next 6 days also by inc 1 day in the binding date.
*
* Logic for navigation of dates
* 1. Find the nearest previous Sunday of the target date.
* 2. Find Start Date's nearest sunday
* 3. subtract target sunday & start date sunday. This will give no of days between these start Date & Target days.
* 4. Divide by 7 to get the index of target date.(As we are considering 7 days in a view.)
* 5. scroll RV to the target index and as we already have the day of the target date mark that index as selected.
*/

class WeeklyViewActivity : AppCompatActivity(),
    WeeklyCalendarAdapter.WeeklyCalendarAdapterListener {
    private var currentPos: Int = 0
    val startDate = Date().time - (DAYS_IN_SINGLE_VIEW * PREVIOUS_WEEK * DAY_IN_MS)
    val endDate = Date().time + (DAYS_IN_SINGLE_VIEW * UPCOMING_WEEK * DAY_IN_MS)

    private lateinit var binding: ActivityWeeklyViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weekly_view)
        addListeners()
        initTasksRecyclerView()
        initWeeklyViewPager()
    }

    private fun addListeners() {
        with(binding) {
            ivChevronLeft.setOnClickListener {
                if (currentPos > 0) {
                    vpWeekView.currentItem = (currentPos - 1)
                    currentPos -= 1
                }
            }
            ivChevronRight.setOnClickListener {
                if (currentPos < vpWeekView.adapter?.itemCount?.minus(1) ?: 0) {
                    vpWeekView.currentItem = (currentPos + 1)
                    currentPos += 1
                }
            }
            tvGoToToday.setOnClickListener {
                scrollToCurrentDate(Date(), startDate.toDate())
            }
            tvMonth.setOnClickListener {
                this@WeeklyViewActivity.openDatePicker(startDate.toDate(), endDate.toDate()) {
                    scrollToCurrentDate(it.time, startDate.toDate())
                }
            }
        }
    }

    private fun initTasksRecyclerView() {
        with(binding.rvTaskList) {
            layoutManager = LinearLayoutManager(this@WeeklyViewActivity, RecyclerView.VERTICAL, false)
            adapter = TaskListAdapter()
        }
    }

    private fun initWeeklyViewPager() {
        with(binding.vpWeekView) {
            adapter =
                WeeklyCalendarAdapter(this@WeeklyViewActivity, startDate.toDate(), endDate.toDate())
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    updateMonth(
                        (binding.vpWeekView.adapter as WeeklyCalendarBaseAdapter).getItem(position)
                    )
                }
            })
        }
        scrollToCurrentDate(Date(), startDate.toDate())
    }

    private fun scrollToCurrentDate(
        targetDate: Date,
        startDate: Date
    ) {
        val startCalendar = Calendar.getInstance(Locale.getDefault())
        startCalendar.time = startDate
        startCalendar.getNearestSunday()

        val targetCalendar = Calendar.getInstance(Locale.getDefault())
        targetCalendar.time = targetDate
        targetCalendar.getNearestSunday()

        val currentDateIndex =
            (targetCalendar.timeInMillis - startCalendar.timeInMillis) / (DAY_IN_MS * DAYS_IN_SINGLE_VIEW)

        with(binding.vpWeekView) {
            post {
                currentItem = (currentDateIndex.toInt())
                (adapter as WeeklyCalendarAdapter).targetDate = targetDate
            }
        }
    }

    fun updateMonth(date: WeeklyCalendarBaseAdapter.Companion.DateHolder) {
        binding.tvMonth.text = date.date.format(DATE_FORMAT_MONTH)
    }

    companion object {
        const val DAY_IN_MS = (1000 * 60 * 60 * 24).toLong()
        const val DAYS_IN_SINGLE_VIEW = 7
        const val PREVIOUS_WEEK = 4
        const val UPCOMING_WEEK = 8
    }

    //Get Data for each date
    override fun onDateSelected(dateMillis: Date) {

    }
}