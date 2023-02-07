package com.example.bettingdemoapp.api

import com.example.bettingdemoapp.domain.model.sportsEvent.SportsEvents
import retrofit2.Response

class Repository(private val apiService: ApiService) {
    suspend fun getSportsEvents(): Response<SportsEvents> {
        return apiService.getSportsEvents()
    }

}