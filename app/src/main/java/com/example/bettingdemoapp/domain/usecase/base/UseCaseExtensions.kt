package com.example.bettingdemoapp.domain.usecase.base

import com.example.bettingdemoapp.domain.model.base.exception.DomainException


@Suppress()
inline fun <T:Any> asDataResult(block:()->T) = try {
    DataResult.Success(block())
}catch (t: DomainException){
    DataResult.Error(t)
}