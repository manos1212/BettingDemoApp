package com.example.bettingdemoapp.domain.usecase.base

import com.example.bettingdemoapp.domain.model.base.exception.DomainException

/**
 * Sealed class representing the types of results that can be returned after the execution of a
 * [UseCase]. Ideally should be returned only the two cases[Success] or [Error] which should include more information
 * about the Success or Error state in each class attributes.
 */

sealed class DataResult<out ResultType> {

    val isSuccess get() = this is Success
    val isError get() = this !is Success


    data class Success<ResultType : Any>(
        val data: ResultType,
    ) : DataResult<ResultType>()

    data class Error(
        val cause: DomainException,
        val errorKey: String = cause.errorKey,
        val isNetworkError: Boolean = cause.isNetworkError,
        val isHttpError: Boolean = cause.isHttpError,
    ) : DataResult<Nothing>()

}