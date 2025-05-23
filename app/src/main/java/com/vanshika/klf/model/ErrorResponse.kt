package com.vanshika.klf.model

data class ErrorResponse(
    val code: String? = null,
    val message: String? = null,
    val data: ErrorData? = null
)