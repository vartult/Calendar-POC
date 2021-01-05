package com.cellfishpool.calendarviews

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cellfishpool.calendarviews.databinding.ActivityMainBinding
import com.cellfishpool.calendarviews.weeklyview.WeeklyViewActivity

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    addListener()
  }

  private fun addListener() {
    with(binding){
      btWeeklyView.setOnClickListener {
        startActivity(Intent(this@MainActivity, WeeklyViewActivity::class.java))
      }
    }
  }
}