package com.example.bettingdemoapp.presentation.main

import com.example.bettingdemoapp.domain.model.sportsEvent.CustomSportCategory
import com.example.bettingdemoapp.ui.mvp.view.base.MvpView

interface MainView : MvpView {
    fun setSportsEventsList(sportCategories: MutableList<CustomSportCategory>)
}