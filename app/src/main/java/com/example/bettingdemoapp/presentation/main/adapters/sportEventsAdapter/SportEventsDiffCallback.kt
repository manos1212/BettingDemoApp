package com.example.bettingdemoapp.presentation.main.adapters.sportEventsAdapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.bettingdemoapp.domain.model.sportsEvent.CustomSportsEvent

class SportEventsDiffCallback : DiffUtil.ItemCallback<CustomSportsEvent>() {

    companion object {
        const val TIME_PAYLOAD = "timePayload"
    }


    override fun getChangePayload(oldItem: CustomSportsEvent, newItem: CustomSportsEvent): Any? {
        if (oldItem.time != newItem.time) {
            return Bundle().apply {
                putString(TIME_PAYLOAD, newItem.time)
            }
        }
        return null
    }

    override fun areItemsTheSame(oldItem: CustomSportsEvent, newItem: CustomSportsEvent): Boolean {
        return oldItem.eventId == newItem.eventId
    }

    override fun areContentsTheSame(
        oldItem: CustomSportsEvent,
        newItem: CustomSportsEvent
    ): Boolean {
        oldItem.apply {
            return oldItem.time == newItem.time && oldItem.isFavourite == newItem.isFavourite
        }
    }
}