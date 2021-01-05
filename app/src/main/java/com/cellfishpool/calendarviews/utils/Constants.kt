package com.cellfishpool.calendarviews.utils

class Constants {
    enum class DayOfWeek(val dayIndex: Int) {
        SUNDAY(1),
        MONDAY(2),
        TUESDAY(3),
        WEDNESDAY(4),
        THURSDAY(5),
        FRIDAY(6),
        SATURDAY(7);

        companion object {
            fun getWeekByIndex(dayIndex: Int): DayOfWeek = values().first {
                it.dayIndex == dayIndex
            }
        }
    }
}