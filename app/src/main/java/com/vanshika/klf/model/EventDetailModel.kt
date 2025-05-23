package com.vanshika.klf.model

data class EventDetailModel(
    val status: String,
    val message: String,
    val data: EventData? = null,
    val pagination: List<Any>? = null,
    val params: Map<String, String>? = null
)