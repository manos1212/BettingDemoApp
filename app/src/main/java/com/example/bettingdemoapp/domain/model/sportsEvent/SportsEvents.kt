package com.example.bettingdemoapp.domain.model.sportsEvent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SportsEvents : ArrayList<SportsEventCategory>(), Parcelable
