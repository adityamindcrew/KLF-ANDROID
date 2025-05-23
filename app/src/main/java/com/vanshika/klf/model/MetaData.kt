package com.vanshika.klf.model

import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("psyem_event_startdate")
    val startDate: String? = null,

    @SerializedName("psyem_event_starttime")
    val startTime: String? = null,

    @SerializedName("psyem_event_enddate")
    val endDate: String? = null,

    @SerializedName("psyem_event_endtime")
    val endTime: String? = null,

    @SerializedName("psyem_event_ticket_price")
    val ticketPrice: String? = null,

    @SerializedName("psyem_event_registration_type")
    val registrationType: String? = null,

    @SerializedName("psyem_event_registration_closing")
    val registrationClosing: String? = null,

    @SerializedName("psyem_event_website")
    val website: String? = null,

    @SerializedName("psyem_event_address")
    val address: String? = null,

    @SerializedName("psyem_event_disclaimer")
    val disclaimer: String? = null,

    @SerializedName("psyem_event_facebook_url")
    val facebookURL: String? = null,

    @SerializedName("psyem_event_instagram_url")
    val instagramURL: String? = null,

    @SerializedName("psyem_event_linkedin_url")
    val linkedinURL: String? = null,

    @SerializedName("psyem_event_twitter_url")
    val twitterURL: String? = null,

    @SerializedName("psyem_event_media_urls")
    val mediaURLs: Any? = null, // consider changing to List<String> if it's a list

    @SerializedName("psyem_event_total_slots")
    val totalSlots: String? = null,

    @SerializedName("psyem_event_used_slots")
    val usedSlots: String? = null
)
