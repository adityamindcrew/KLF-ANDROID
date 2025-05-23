package com.vanshika.klf.model

import com.google.gson.annotations.SerializedName

data class EventData(
    @SerializedName("ID")
    val id: Int? = null,

    val title: String? = null,
    val excerpt: String? = null,
    val image: String? = null,

    @SerializedName("meta_data")
    val metaData: MetaData? = null
)