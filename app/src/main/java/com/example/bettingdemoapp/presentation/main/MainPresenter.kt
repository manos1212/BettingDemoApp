package com.example.bettingdemoapp.presentation.main

import com.example.bettingdemoapp.domain.model.sportsEvent.CustomSportCategory
import com.example.bettingdemoapp.domain.model.sportsEvent.CustomSportsEvent
import com.example.bettingdemoapp.domain.model.sportsEvent.SportsEventCategory
import com.example.bettingdemoapp.ui.mvp.presenter.base.MvpPresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainPresenter @Inject constructor() : MvpPresenter<MainView>() {

    fun init(sportCategoriesList: MutableList<SportsEventCategory>) {
        val sportCategories: MutableList<CustomSportCategory> = mutableListOf()
        var sportEventsPerCategory: MutableList<CustomSportsEvent>
        sportCategoriesList.forEach { sportsEventCat ->
            sportEventsPerCategory = mutableListOf()
            sportsEventCat.e.forEach {
                val competitors = it.d
                val delimiter = "-"
                val splitCompetitors = competitors.split(delimiter)
                val competitor1 = splitCompetitors[0]
                val competitor2 = splitCompetitors[1]
                sportEventsPerCategory.add(
                    CustomSportsEvent(
                        eventId = it.i,
                        competitor1 = competitor1,
                        competitor2 = competitor2,
                        time = getDateTime(it.tt.toString()) ?: ""
                    )
                )
            }

            sportCategories.add(
                CustomSportCategory(
                    sportName = sportsEventCat.d,
                    sportEvents = sportEventsPerCategory
                )
            )
        }

        ifViewAttached { view -> view.setSportsEventsList(sportCategories) }

    }

    private fun getDateTime(s: String): String? {
        return try {
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

}