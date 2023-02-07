package com.example.bettingdemoapp.domain.model.sportsEvent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomSportsEvent(
    val eventId: String,
    val competitor1: String,
    val competitor2: String,
    val time: String,
    val isFavourite: Boolean = false
) : Parcelable