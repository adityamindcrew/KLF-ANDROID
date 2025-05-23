package com.vanshika.klf.remote

import com.vanshika.klf.model.*
import retrofit2.Call
import retrofit2.http.*
import kotlinx.coroutines.Deferred
import kotlin.coroutines.Continuation

interface ApiService {

    @GET("event/detail/")
    fun getEventDetail(
        @Query("id") eventId: Int,
        @Query("user_token") userToken: String?
    ): Call<EventDetailModel>

    @GET("event/list")
    fun getEvents(
        @Query("from_date") fromDate: String? = null,
        @Query("to_date") toDate: String? = null,
        @Query("search_key") searchKey: String? = null,
        @Query("signup_type") signupType: String? = null,
        @Query("paged") page: Int = 0,
        @Query("user_token") userToken: String? = null
    ): Call<EventListModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("wp_username") username: String,
        @Field("wp_password") password: String
    ): Call<BaseResponse<UserData>>

    @FormUrlEncoded
    @POST("logout")
    suspend fun logoutUser(
        @Field("user_id") userId: Int,
        @Field("user_token") userToken: String
    ): Call<LogoutResponse>
}
