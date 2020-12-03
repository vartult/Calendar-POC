package com.cellfishpool.calendarpoc.calendarviews.weekview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cellfishpool.calendarpoc.R
import com.cellfishpool.calendarpoc.databinding.FragmentDayWiseInfoBinding
import com.cellfishpool.calendarpoc.model.Event

class DayWiseInfoFragment : Fragment() {

  private lateinit var binding: FragmentDayWiseInfoBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_day_wise_info, container, false)
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    handleRV()
  }

  private fun handleRV() {
    binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
    binding.rvTasks.adapter = DayWiseInfoAdapter()
    (binding.rvTasks.adapter as DayWiseInfoAdapter).data = listOf(
        Event("Hey", "Hey"), Event("bye", "Hey"), Event("new", "Hey"), Event("million", "Hey"),
        Event("things", "Hey")
    ).shuffled()
  }
}