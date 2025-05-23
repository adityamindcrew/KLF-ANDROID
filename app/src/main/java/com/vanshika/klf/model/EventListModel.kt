package com.vanshika.klf.model

import com.google.gson.annotations.SerializedName

data class EventListModel(
    val status: String,
    val message: String,
    val data: Map<String, Event>,
    val params: Map<String, String>
)

