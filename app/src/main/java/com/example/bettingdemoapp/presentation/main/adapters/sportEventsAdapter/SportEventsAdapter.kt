package com.example.bettingdemoapp.presentation.main.adapters.sportEventsAdapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bettingdemoapp.R
import com.example.bettingdemoapp.databinding.LayoutSportEventBinding
import com.example.bettingdemoapp.domain.model.sportsEvent.CustomSportsEvent
import com.example.bettingdemoapp.presentation.main.adapters.sportEventsAdapter.SportEventsDiffCallback.Companion.TIME_PAYLOAD
import timber.log.Timber

class SportEventsAdapter(private var onFavClick: (result: Int) -> Unit) :
    ListAdapter<CustomSportsEvent, SportEventsAdapter.ViewHolderSportEvent>(SportEventsDiffCallback()) {

    //    private val sportEventsList = data
    private lateinit var sportEventsBinding: LayoutSportEventBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSportEvent {
        sportEventsBinding =
            LayoutSportEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderSportEvent(sportEventsBinding)
    }


    override fun onBindViewHolder(
        holder: ViewHolderSportEvent,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = payloads[0] as Bundle
            val time = item.getString(TIME_PAYLOAD)
            holder.updateTimeView(time.toString())
            return
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ViewHolderSportEvent, position: Int) {
        val item = currentList[position]
        holder.bind(item)
        Timber.d("ViewHolderSportEvent bind fun called")

    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class ViewHolderSportEvent(private val itemBinding: LayoutSportEventBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: CustomSportsEvent) {
            itemBinding.competitor1.text = item.competitor1
            itemBinding.competitor2.text = item.competitor2
            itemBinding.startTimeTextView.text = item.time
            itemBinding.favoriteIcon.setImageResource(if (item.isFavourite) R.drawable.ic_star_filled else R.drawable.ic_star_border)
            itemBinding.favoriteIcon.setOnClickListener {
                if (!item.isFavourite) {
                    (it as ImageView).setImageResource(R.drawable.ic_star_filled)
                    onFavClick(adapterPosition)
                }
            }
        }

        fun updateTimeView(time: String) {
            itemBinding.startTimeTextView.text = time
        }

    }


}