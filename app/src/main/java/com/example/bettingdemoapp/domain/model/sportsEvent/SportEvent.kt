package com.example.bettingdemoapp.domain.model.sportsEvent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SportEvent(
    val d: String,
    val i: String,
    val sh: String,
    val si: String,
    val tt: Int,
    val fav: Boolean = false
) : Parcelable