package com.vanshika.klf.model

data class LogoutResponse(
    val status: String,
    val message: String,
    val data: List<UserData>
)
