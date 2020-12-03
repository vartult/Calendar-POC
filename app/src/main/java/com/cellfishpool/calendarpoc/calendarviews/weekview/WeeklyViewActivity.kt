package com.cellfishpool.calendarpoc.calendarviews.weekview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.PagerSnapHelper
import com.cellfishpool.calendarpoc.R
import com.cellfishpool.calendarpoc.calendarutils.models.RecyclerCalendarConfiguration
import com.cellfishpool.calendarpoc.calendarviews.weekview.HorizontalRecyclerCalendarAdapter.OnDateSelected
import com.cellfishpool.calendarpoc.databinding.ActivityWeeklyViewBinding
import java.util.*

class WeeklyViewActivity : AppCompatActivity(), OnDateSelected {
    private lateinit var date: Date
    private val startCal = Calendar.getInstance()
    private val endCal = Calendar.getInstance()
    private lateinit var binding: ActivityWeeklyViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weekly_view)
        initDates()
        val config = configureRecyclerCalendarConfiguration()
        configureRecyclerView(config)
    }

    private fun configureRecyclerView(configuration: RecyclerCalendarConfiguration) {
        val calendarAdapterHorizontal: HorizontalRecyclerCalendarAdapter =
            HorizontalRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = endCal.time,
                configuration = configuration,
                selectedDate = date,
                this
            )

        binding.rvDays.adapter = calendarAdapterHorizontal

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvDays)
    }

    private fun configureRecyclerCalendarConfiguration() = RecyclerCalendarConfiguration(
        calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL,
        calendarLocale = Locale.getDefault(),
        includeMonthHeader = true
    )

    private fun initDates() {
        date = Date()
        date.time = System.currentTimeMillis()
        endCal.time = date
        endCal.add(Calendar.MONTH, 3)
    }

    override fun onDateSelected(date: Date) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(binding.fragmentContainerView.id, DayWiseInfoFragment(), "NewFragmentTag")
        ft.commit()
    }

}