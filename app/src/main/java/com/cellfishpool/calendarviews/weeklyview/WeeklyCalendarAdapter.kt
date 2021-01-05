package com.cellfishpool.calendarviews.weeklyview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cellfishpool.calendarviews.R
import com.cellfishpool.calendarviews.databinding.LayoutWeekViewBinding
import com.cellfishpool.calendarviews.databinding.LayoutWeekViewDateItemBinding
import com.cellfishpool.calendarviews.utils.*
import com.cellfishpool.calendarviews.utils.Constants.DayOfWeek.SUNDAY
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeeklyCalendarAdapter(
  private val weeklyCalendarAdapterListener: WeeklyCalendarAdapterListener,
  startDate: Date,
  endDate: Date
) : WeeklyCalendarBaseAdapter(startDate, endDate) {

  var targetDate: Date? = null
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  inner class WeeklyCalendarDayView(private val binding: LayoutWeekViewBinding) :
    RecyclerView.ViewHolder(
      binding.root
    ) {
    fun bind(calendarItem: Companion.DateHolder) {
      clearViews()
      with(binding) {
        handleDateValidity()
        targetDate?.let { safeTargetDate ->
        val targetCalendar = Calendar.getInstance(Locale.getDefault())
          targetCalendar.time = safeTargetDate
          val thisDayOfWeek: Int = targetCalendar.get(Calendar.DAY_OF_WEEK)
          targetCalendar.getNearestSunday()

          if (targetCalendar.time == calendarItem.date) {
            handleWeekOfDay(thisDayOfWeek, calendarItem)
          }
        }

        val currentCalendar = Calendar.getInstance(Locale.getDefault())
        currentCalendar.time = Date()
        val thisDayOfWeek: Int = currentCalendar.get(Calendar.DAY_OF_WEEK)
        currentCalendar.getNearestSunday()

        if (currentCalendar.time == calendarItem.date) {
          handleWeekOfDay(thisDayOfWeek, calendarItem, true)
        }

        binding.rootLayout.forEachIndexed { index, view ->
          val binding = DataBindingUtil.getBinding<LayoutWeekViewDateItemBinding>(view)
          binding?.let { layoutWeekView ->
            layoutWeekView.tvDate.text = calendarItem.date.plusDays(index)
              .getDateOfMonth()
            addListener(layoutWeekView, calendarItem.date.plusDays(index))
          }
        }
      }
    }

    private fun LayoutWeekViewBinding.handleWeekOfDay(
      thisDayOfWeek: Int,
      calendarItem: Companion.DateHolder,
      isToday: Boolean = false
    ) {
      when (Constants.DayOfWeek.getWeekByIndex(thisDayOfWeek)) {
        SUNDAY -> handleClickedDate(layoutDateViewOne, (calendarItem.date), isToday)
        Constants.DayOfWeek.MONDAY -> handleClickedDate(
          layoutDateViewTwo, (calendarItem.date).plusDays(1), isToday
        )
        Constants.DayOfWeek.TUESDAY -> handleClickedDate(
          layoutDateViewThree, (calendarItem.date).plusDays(2), isToday
        )
        Constants.DayOfWeek.WEDNESDAY -> handleClickedDate(
          layoutDateViewFour, (calendarItem.date).plusDays(3), isToday
        )
        Constants.DayOfWeek.THURSDAY -> handleClickedDate(
          layoutDateViewFive, (calendarItem.date).plusDays(4), isToday
        )
        Constants.DayOfWeek.FRIDAY -> handleClickedDate(
          layoutDateViewSix, (calendarItem.date).plusDays(5), isToday
        )
        Constants.DayOfWeek.SATURDAY -> handleClickedDate(
          layoutDateViewSeven, (calendarItem.date).plusDays(6), isToday
        )
      }
    }

    private fun addListener(
      binding: LayoutWeekViewDateItemBinding,
      date: Date
    ) {
      binding.tvDate.setOnClickListener {
        targetDate = date
        handleClickedDate(binding, date)
      }
    }

    private fun handleClickedDate(
      itemHolder: LayoutWeekViewDateItemBinding,
      calendarItem: Date,
      isToday: Boolean = false
    ) {
      if (isToday) {
        itemHolder.tvDate.setTextColor(
          ContextCompat.getColor(itemView.context,R.color.colorBlack)
        )
        itemHolder.selectedArrow.visible(false)
        itemHolder.tvDate.setBackgroundResource(
          R.drawable.bg_weekly_calendar_today
        )
      } else {
        itemHolder.tvDate.setTextColor(
          ContextCompat.getColor(itemView.context,R.color.colorWhite)
        )
        itemHolder.tvDate.setBackgroundResource(
          R.drawable.bg_weekly_calendar_item_selected
        )
        itemHolder.selectedArrow.visible(true)
      }
      weeklyCalendarAdapterListener.onDateSelected(calendarItem)
    }

    private fun clearViews() {
      binding.rootLayout.forEachIndexed { index, view ->
        val binding = DataBindingUtil.getBinding<LayoutWeekViewDateItemBinding>(view)
        binding?.let { layoutWeekView ->
          layoutWeekView.tvDate.setTextColor(
            ContextCompat.getColor(itemView.context,R.color.colorBlack)
          )
          layoutWeekView.selectedArrow.visible(false)
          layoutWeekView.tvDate.setBackgroundResource(
            R.drawable.bg_weekly_calendar_item
          )
        }
      }
    }

    /*
    * TODO: Check if the date is in the valid range i.e 4 weeks behind current date & 8 weeks ahead of current date
    * dates ranging outside needs to be disabled
    * */
    private fun handleDateValidity() {
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): WeeklyCalendarDayView {
    val binding = LayoutWeekViewBinding
      .inflate(LayoutInflater.from(parent.context), parent, false)
    return WeeklyCalendarDayView(binding)
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int,
    calendarItem: Companion.DateHolder
  ) {
    (holder as WeeklyCalendarDayView).bind(calendarItem)
  }

  interface WeeklyCalendarAdapterListener {
    fun onDateSelected(dateMillis: Date)
  }
}
