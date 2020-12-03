package com.cellfishpool.calendarpoc.calendarutils.models

import java.util.*

open class RecyclerCalendarConfiguration(
    val calenderViewType: CalenderViewType,
    val calendarLocale: Locale,
    val includeMonthHeader: Boolean
) {
    enum class CalenderViewType {
        HORIZONTAL, VERTICAL
    }
}