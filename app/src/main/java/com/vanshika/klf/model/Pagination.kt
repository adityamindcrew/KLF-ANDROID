package com.vanshika.klf.model

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("current_page")
    val currentPage: Int,

    @SerializedName("total_page")
    val totalPage: Int,

    @SerializedName("posts_count")
    val postsCount: Int,

    @SerializedName("limit")
    val limit: Int
)
