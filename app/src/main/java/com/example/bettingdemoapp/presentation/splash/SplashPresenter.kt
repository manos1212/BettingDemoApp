package com.example.bettingdemoapp.presentation.splash

import com.example.bettingdemoapp.domain.model.sportsEvent.SportsEventCategory
import com.example.bettingdemoapp.domain.model.sportsEvent.SportsEvents
import com.example.bettingdemoapp.domain.usecase.base.DataResult
import com.example.bettingdemoapp.domain.usecase.sportsEvents.SportsEventsUseCase
import com.example.bettingdemoapp.ui.mvp.presenter.base.MvpPresenter
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val getSportsEventsUseCase: SportsEventsUseCase
) : MvpPresenter<SplashView>() {

    fun init() {
        getSportsEvents()
    }

    private fun getSportsEvents() {
        ifViewAttached { view ->
            uiScope.launch {
                when (val sportsEventsUseCase = executeNetworkCall {
                    getSportsEventsUseCase.invoke()
                }) {
                    is DataResult.Success -> {
                        if (sportsEventsUseCase.data.isSuccessful) {
                            val result = sportsEventsUseCase.data.body()
                            val sportEventsCategories: MutableList<SportsEventCategory> =
                                mutableListOf()
                            result?.let {
                                sportEventsCategories.addAll(it)
                            }
                            view.proceedToMainActivity(sportEventsCategories)
                        } else {
                            Timber.d("call error")
                            ///show error // proceed with empty list
                            view.proceedToMainActivity(SportsEvents())
                        }
                    }
                    is DataResult.Error -> {
                        //show error // proceed with empty list
                        view.proceedToMainActivity(SportsEvents())
                    }
                }
            }
        }


    }
}