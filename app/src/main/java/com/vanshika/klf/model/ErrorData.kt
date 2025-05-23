package com.vanshika.klf.model

data class ErrorData(
    val status: Int? = null,
    val params: Map<String, String>? = null,
    val details: List<Any>? = null
)