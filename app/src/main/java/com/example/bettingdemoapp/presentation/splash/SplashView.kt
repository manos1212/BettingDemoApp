package com.example.bettingdemoapp.presentation.splash

import com.example.bettingdemoapp.domain.model.sportsEvent.SportsEventCategory
import com.example.bettingdemoapp.ui.mvp.view.base.MvpView

interface SplashView : MvpView {
    fun proceedToMainActivity(sportsCategories: MutableList<SportsEventCategory>)
}