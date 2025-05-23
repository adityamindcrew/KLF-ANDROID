package com.vanshika.klf.model

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("ID")
    val id: String?,
    val title: String?,
    val excerpt: String?,
    val image: String?,
    @SerializedName("meta_data")
    val metaData: MetaData?
)