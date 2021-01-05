package com.cellfishpool.calendarviews.weeklyview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cellfishpool.calendarviews.databinding.ItemWeeklyViewTaskBinding
import com.cellfishpool.calendarviews.weeklyview.TaskListAdapter.ItemTaskViewHolder

class TaskListAdapter : RecyclerView.Adapter<ItemTaskViewHolder>() {

  inner class ItemTaskViewHolder(private val binding: ItemWeeklyViewTaskBinding) : RecyclerView.ViewHolder(
    binding.root
  ) {
    fun bind() {
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ItemTaskViewHolder {
    val binding =
      ItemWeeklyViewTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ItemTaskViewHolder(binding)
  }

  override fun onBindViewHolder(
    holder: ItemTaskViewHolder,
    position: Int
  ) {
    holder.bind()
  }

  override fun getItemCount(): Int = 3
}
