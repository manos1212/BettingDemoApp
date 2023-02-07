package com.example.bettingdemoapp.domain.model.sportsEvent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SportsEventCategory(
    val d: String,
    val e: MutableList<SportEvent>,
    val i: String
) : Parcelable
