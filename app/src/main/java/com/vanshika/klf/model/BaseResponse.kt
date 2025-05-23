package com.vanshika.klf.model

data class BaseResponse<T>(
    val status: String?,
    val message: String?,
    val data: T?
)