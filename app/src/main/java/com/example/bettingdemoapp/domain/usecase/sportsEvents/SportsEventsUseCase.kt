package com.example.bettingdemoapp.domain.usecase.sportsEvents

import com.example.bettingdemoapp.api.Repository
import com.example.bettingdemoapp.domain.model.sportsEvent.SportsEvents
import com.example.bettingdemoapp.domain.usecase.base.DataResult
import com.example.bettingdemoapp.domain.usecase.base.UseCase
import com.example.bettingdemoapp.domain.usecase.base.asDataResult
import retrofit2.Response
import javax.inject.Inject


class SportsEventsUseCase @Inject constructor(
    private val repository: Repository,
) : UseCase {
    suspend operator fun invoke(): DataResult<Response<SportsEvents>> {
        return asDataResult {
            repository.getSportsEvents()
        }
    }
}