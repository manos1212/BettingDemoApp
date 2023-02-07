package com.example.bettingdemoapp.domain.model.sportsEvent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomSportCategory(
    val sportName: String,
    var sportEvents: MutableList<CustomSportsEvent>,
) : Parcelable
