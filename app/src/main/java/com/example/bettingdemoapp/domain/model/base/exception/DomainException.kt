package com.example.bettingdemoapp.domain.model.base.exception

data class DomainException(
    val errorKey: String,
    val throwable: Throwable,
    val isNetworkError: Boolean = false,
    val isHttpError: Boolean = false,
) : Throwable()