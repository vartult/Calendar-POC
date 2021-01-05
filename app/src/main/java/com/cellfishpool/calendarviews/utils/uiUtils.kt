package com.cellfishpool.calendarviews.utils

import android.view.View

fun View.visible(toShow: Boolean) = if (toShow) {
    this.visibility = View.VISIBLE
} else {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}