package com.cellfishpool.calendarviews.utils

import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val DATE_FORMAT_MMM_DD_YYYY = "MMM dd, yyyy"
private const val TIME_FORMAT_HH_MM_A = "hh:mm a"
const val DATE_FORMAT_MMM_DD_YYYY_HH_MM = "MMM dd, yyyy @ hh:mm"
const val DATE_FORMAT_MM_DD_YYYY = "MM/dd/yyyy"
const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
const val DATE_FORMAT_MMM_YY = "MMM, yy"
const val DATE_FORMAT_MMM_DD = "MMM dd"
const val DATE_FORMAT_COMMON = DATE_FORMAT_MMM_DD_YYYY
const val DATE_FORMAT_DD = "dd"
const val DATE_FORMAT_MONTH = "MMMM, yyyy"

fun String.toDate(): Date? {
  return try {
    val sdf = SimpleDateFormat(UTC_DATE_FORMAT, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.parse(this)
  } catch (exception: Exception) {
    null
  }
}

fun String.convertUtcToLocal(): String {
  return try {
    val sdf = SimpleDateFormat(UTC_DATE_FORMAT, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    val date = sdf.parse(this)
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date!!)
  } catch (exception: Exception) {
    ""
  }
}

fun String.convertUtcToFormat(
  format: String,
  ignoreZone: Boolean = false
): String {
  return try {
    val sdf = SimpleDateFormat(UTC_DATE_FORMAT, Locale.getDefault())
    if (ignoreZone.not()) {
      sdf.timeZone = TimeZone.getTimeZone("UTC")
    }
    val date = sdf.parse(this)

    val formattedSdf = SimpleDateFormat(format, Locale.getDefault())
    formattedSdf.timeZone = TimeZone.getDefault()
    return formattedSdf.format(date!!)
  } catch (exception: Exception) {
    ""
  }
}

fun String.getTimeInMillis(ignoreZone: Boolean = false): Long {
  return try {
    val sdf = SimpleDateFormat(UTC_DATE_FORMAT, Locale.getDefault())
    val date = if (ignoreZone) {
      sdf.parse(this)
    } else {
      sdf.parse(this.convertUtcToLocal())
    }
    return date?.time ?: 0L
  } catch (exception: Exception) {
    0L
  }
}

fun Long.toDate(): Date {
  val calendar = Calendar.getInstance()
  calendar.timeInMillis = this
  return calendar.time
}

fun Date.hoursAgo(): Int {
  return (System.currentTimeMillis() - this.time).toInt() / (1000 * 60 * 60)
}

fun Long.formatForChannelListing(): String {
  val date = this.toDate()
  return when {
    date.isToday() -> {
      date.time() ?: ""
    }
    date.isYesterday() -> {
      "Yesterday"
    }
    else -> {
      date.date() ?: ""
    }
  }
}

fun Date.isToday(): Boolean {
  return DateUtils.isToday(this.time)
}

fun Date.getDateOfMonth(): String {
  return SimpleDateFormat(DATE_FORMAT_DD, Locale.getDefault()).format(this)
}

fun Date.isYesterday(): Boolean {
  return DateUtils.isToday(this.time + DateUtils.DAY_IN_MILLIS)
}

fun Date.dayOfWeek(): String {
  return SimpleDateFormat("EE", Locale.getDefault()).format(this)
}

fun Date.plusDays(days: Int): Date {
  val c = Calendar.getInstance()
  c.time = this
  c.add(Calendar.DATE, days)
  return c.time
}

fun Date.date(): String? {
  return this.format(DATE_FORMAT_COMMON)
}

fun Date.time(): String? {
  return this.format(TIME_FORMAT_HH_MM_A)
}

fun Date.deviceFormatTime(context: Context) = android.text.format.DateFormat.getTimeFormat(context)
  .format(this)

fun Date.format(
  outputFormat: String,
  timeZone: TimeZone = TimeZone.getDefault()
): String? {
  val output = SimpleDateFormat(outputFormat, Locale.getDefault())
  output.timeZone = timeZone
  return try {
    output.format(time)
  } catch (e: Exception) {
    e.printStackTrace()
    null
  }
}

fun String.format(format: String) {
  val sdf = SimpleDateFormat(format, Locale.getDefault())
  sdf.format(this)
}

fun String.convertMMDDYYYYToUTCFormat(): String? {
  if (this.isBlank()) {
    return null
  }
  val date = this.split("/")
  val month = date[0]
  val day = date[1]
  val year = date[2]

  return "$year-$month-${day}T00:00:00.000Z"
}

fun getCurrentDateUtcFormat(): String {
  val sdf = SimpleDateFormat(UTC_DATE_FORMAT, Locale.getDefault())
  sdf.timeZone = TimeZone.getTimeZone("UTC")

  return sdf.format(Date())
}

/**
 * This accepts time in hh:mm
 */
fun String.timeToMillis(): Long {
  val hhmm = this.split(":")
  var min = (hhmm.first()
    .takeIf { it.isNotEmpty() }
    ?.toInt() ?: 0) * 60
  if (hhmm.size > 1) {
    min += hhmm[1].toInt()
  }
  return min * 60 * 1000L
}


fun Calendar.getNearestSunday(): Calendar {
  var thisDayOfWeek: Int = this.get(Calendar.DAY_OF_WEEK)

  if (thisDayOfWeek - 1 < 0) {
    thisDayOfWeek = 7 + (thisDayOfWeek - 1)
    this.add(Calendar.DATE, -1 * thisDayOfWeek)
  } else {
    this.add(Calendar.DATE, -1 * (thisDayOfWeek - 1))
  }

  this[Calendar.HOUR_OF_DAY] = 0
  this[Calendar.MINUTE] = 0
  this[Calendar.SECOND] = 0
  this[Calendar.MILLISECOND] = 0

  return this
}

fun String.formatDateIgnoringZone(format: String = DATE_FORMAT_COMMON) =
  convertUtcToFormat(format, ignoreZone = true)
