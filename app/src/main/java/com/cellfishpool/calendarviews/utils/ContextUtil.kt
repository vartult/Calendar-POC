package com.cellfishpool.calendarviews.utils

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun Context.openDatePicker(
    minDate: Date? = null,
    maxDate: Date? = null,
    onDateSelected: (Calendar) -> Unit
) {
    val calendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            onDateSelected(calendar)
        }

    DatePickerDialog(
        this, datePickerOnDataSetListener, calendar
            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).run {
        minDate?.time?.also { datePicker.minDate = it }
        maxDate?.time?.also { datePicker.maxDate = it }
        show()
    }
}