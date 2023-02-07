package com.example.bettingdemoapp.api

import com.example.bettingdemoapp.domain.model.sportsEvent.SportsEvents
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("sports")
    suspend fun getSportsEvents(): Response<SportsEvents>

}