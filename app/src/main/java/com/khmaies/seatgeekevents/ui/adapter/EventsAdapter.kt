package com.khmaies.seatgeekevents.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.khmaies.seatgeekevents.R
import com.khmaies.seatgeekevents.databinding.EventListItemBinding
import com.khmaies.seatgeekevents.model.Event
import com.khmaies.seatgeekevents.utils.AppUtils.convertUtcToLocalDateTime
import com.khmaies.seatgeekevents.utils.AppUtils.formatPrice

class EventsAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private val eventArrayList = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: EventListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.event_list_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(eventArrayList[position])
    }

    override fun getItemCount(): Int = eventArrayList.size

    fun setData(newEvent: List<Event>) {
        eventArrayList.clear()
        eventArrayList.addAll(newEvent)
        notifyDataSetChanged()
    }

    fun setNextData(moreEvents: List<Event>) {
        eventArrayList.addAll(moreEvents)
        notifyItemRangeInserted(eventArrayList.size - moreEvents.size, moreEvents.size)
    }

    inner class ViewHolder(private val binding: EventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindItems(event: Event) {

            binding.apply {

                eventName.text = event.eventName
                eventDateTime.text = convertUtcToLocalDateTime(event.eventDate)
                eventPrice.text = formatPrice(event.eventStats.eventPrice)
                itemView.setOnClickListener {
                    onClickListener.onClick(event)
                }
            }
        }
    }


    class OnClickListener(val clickListener: (event: Event) -> Unit) {
        fun onClick(event: Event) = clickListener(event)
    }
}