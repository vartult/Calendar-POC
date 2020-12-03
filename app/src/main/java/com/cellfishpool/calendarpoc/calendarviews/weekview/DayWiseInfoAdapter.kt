package com.cellfishpool.calendarpoc.calendarviews.weekview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cellfishpool.calendarpoc.databinding.ItemDailyTaskBinding
import com.cellfishpool.calendarpoc.model.Event

class DayWiseInfoAdapter : RecyclerView.Adapter<DayWiseInfoAdapter.DayEventsItemHolder>() {

    private var data = emptyList<Event>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DayEventsItemHolder(private val binding: ItemDailyTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            with(binding) {
                tvEventTitle.text = event.title
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayEventsItemHolder {
        val binding = ItemDailyTaskBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DayEventsItemHolder(binding)
    }

    override fun onBindViewHolder(holder: DayEventsItemHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}