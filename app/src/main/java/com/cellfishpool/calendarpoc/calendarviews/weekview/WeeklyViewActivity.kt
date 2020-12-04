package com.cellfishpool.calendarpoc.calendarviews.weekview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.PagerSnapHelper
import com.cellfishpool.calendarpoc.R
import com.cellfishpool.calendarpoc.calendarutils.CalendarUtils
import com.cellfishpool.calendarpoc.calendarutils.models.RecyclerCalendarConfiguration
import com.cellfishpool.calendarpoc.calendarviews.weekview.HorizontalRecyclerCalendarAdapter.OnDateSelected
import com.cellfishpool.calendarpoc.databinding.ActivityWeeklyViewBinding
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeeklyViewActivity : AppCompatActivity(), OnDateSelected {
  private lateinit var date: Date
  private val startCal = Calendar.getInstance()
  private val endCal = Calendar.getInstance()
  private lateinit var binding: ActivityWeeklyViewBinding
  private lateinit var configuration: RecyclerCalendarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_weekly_view)
    initDates()
    configuration = configureRecyclerCalendarConfiguration()
    configureRecyclerView(configuration)
  }

  private fun configureRecyclerView(configuration: RecyclerCalendarConfiguration) {
    val calendarAdapterHorizontal =
      HorizontalRecyclerCalendarAdapter(
          startDate = startCal.time,
          endDate = endCal.time,
          configuration = configuration,
          selectedDate = date,
          currentDate = date,
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
    startCal.time = date
    startCal.add(Calendar.MONTH, -8)
    endCal.add(Calendar.MONTH, 4)
  }

  override fun onDateSelected(date: Date) {
    binding.tvDate.text = CalendarUtils.dateStringFromFormat(
        locale = configuration.calendarLocale, date = date, format = CalendarUtils.LONG_DATE_FORMAT
    )
    val ft = supportFragmentManager.beginTransaction()
    ft.replace(binding.fragmentContainerView.id, DayWiseInfoFragment(), "NewFragmentTag")
    ft.commit()
  }
}