package com.vanshika.klf.model

import com.google.gson.annotations.SerializedName

data class EventDetail(
    @SerializedName("psyem_event_startdate") val eventStartDate: String?,
    @SerializedName("psyem_event_starttime") val eventStartTime: String?,
    @SerializedName("psyem_event_enddate") val eventEndDate: String?,
    @SerializedName("psyem_event_endtime") val eventEndTime: String?,
    @SerializedName("psyem_event_ticket_price") val eventTicketPrice: String?,
    @SerializedName("psyem_event_registration_type") val eventRegistrationType: String?,
    @SerializedName("psyem_event_registration_closing") val eventRegistrationClosing: String?,
    @SerializedName("psyem_event_website") val eventWebsite: String?,
    @SerializedName("psyem_event_address") val eventAddress: String?,
    @SerializedName("psyem_event_disclaimer") val eventDisclaimer: String?,
    @SerializedName("psyem_event_facebook_url") val facebookURL: String?,
    @SerializedName("psyem_event_instagram_url") val instagramURL: String?,
    @SerializedName("psyem_event_linkedin_url") val linkedinURL: String?,
    @SerializedName("psyem_event_twitter_url") val twitterURL: String?,
    @SerializedName("psyem_event_media_urls") val mediaURLs: List<String>?,
    @SerializedName("psyem_event_total_slots") val totalSlots: String?,
    @SerializedName("psyem_event_used_slots") val usedSlots: String?
)
