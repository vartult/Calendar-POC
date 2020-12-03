package com.cellfishpool.calendarpoc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cellfishpool.calendarpoc.databinding.ActivityMainBinding
import com.cellfishpool.calendarpoc.calendarviews.weekview.WeeklyViewActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        addListener()
    }

    private fun addListener() {
        with(binding) {
            btWeekView.setOnClickListener {
                startActivity(Intent(this@MainActivity, WeeklyViewActivity::class.java))
            }
            btMonthView.setOnClickListener {

            }
        }
    }
}