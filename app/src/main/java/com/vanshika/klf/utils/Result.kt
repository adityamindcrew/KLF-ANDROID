package com.vanshika.klf.utils

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Failure(val exception: Throwable) : Result<Nothing>()

    object Loading : Result<Nothing>()
}
