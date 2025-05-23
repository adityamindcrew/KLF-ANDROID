package com.vanshika.klf.remote

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.vanshika.klf.model.*
import com.vanshika.klf.utils.Result
import com.vanshika.klf.utils.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventRepository(private val context: Context) {

    private val _eventList = MutableLiveData<Result<EventListModel>>()
    val eventList: LiveData<Result<EventListModel>> = _eventList

    private val _eventDetail = MutableLiveData<Result<EventDetailModel>>()
    val eventDetail: LiveData<Result<EventDetailModel>> = _eventDetail

    private val _loginResult = MutableLiveData<Result<UserData>>()
    val loginResult: LiveData<Result<UserData>> = _loginResult

    private val _logoutResult = MutableLiveData<Result<Unit>>()
    val logoutResult: LiveData<Result<Unit>> = _logoutResult

    fun fetchEvents(
        fromDate: String?,
        toDate: String?,
        searchKey: String?,
        signupType: String?,
        page: Int
    ) {
        _eventList.postValue(Result.Loading)
        val userToken = TokenManager.getToken(context)

        RetrofitClient.getInstance(context).getEvents(fromDate, toDate, searchKey, signupType, page, userToken)
            .enqueue(object : retrofit2.Callback<EventListModel> {
                override fun onResponse(
                    call: retrofit2.Call<EventListModel>,
                    response: retrofit2.Response<EventListModel>
                ) {
                    if (response.isSuccessful) {
                        val eventData = response.body()
                        if (eventData != null) {
                            _eventList.postValue(Result.Success(eventData))
                        } else {
                            _eventList.postValue(Result.Failure(Exception("No Data Found")))
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("API_ERROR", "Error response body: $errorBody")
                        if (errorBody.isNullOrEmpty()) {
                            _eventList.postValue(Result.Failure(Exception("Failed: ${response.code()} ${response.message()}")))
                        } else {
                            try {
                                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                                val message = errorResponse.message ?: "API Error"
                                _eventList.postValue(Result.Failure(Exception(message)))
                            } catch (e: Exception) {
                                _eventList.postValue(Result.Failure(Exception("Failed to parse error response")))
                            }
                        }
                    }
                }

                override fun onFailure(call: retrofit2.Call<EventListModel>, t: Throwable) {
                    _eventList.postValue(Result.Failure(t))
                    Log.e("API_ERROR", "Error fetching events", t)
                }
            })
    }

    fun fetchEventDetail(eventId: Int) {
        _eventDetail.postValue(Result.Loading)
        val userToken = TokenManager.getToken(context)

        RetrofitClient.getInstance(context).getEventDetail(eventId, userToken)
            .enqueue(object : retrofit2.Callback<EventDetailModel> {
                override fun onResponse(
                    call: retrofit2.Call<EventDetailModel>,
                    response: retrofit2.Response<EventDetailModel>
                ) {
                    if (response.isSuccessful) {
                        val eventDetailResponse = response.body()
                        if (eventDetailResponse != null) {
                            _eventDetail.postValue(Result.Success(eventDetailResponse))
                        } else {
                            _eventDetail.postValue(Result.Failure(Exception("Empty response body")))
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("API_ERROR", "Error response body: $errorBody")
                        if (errorBody.isNullOrEmpty()) {
                            _eventDetail.postValue(Result.Failure(Exception("Failed: ${response.code()} ${response.message()}")))
                        } else {
                            try {
                                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                                val message = errorResponse.message ?: "API Error"
                                _eventDetail.postValue(Result.Failure(Exception(message)))
                            } catch (e: Exception) {
                                _eventDetail.postValue(Result.Failure(Exception("Failed to parse error response")))
                            }
                        }
                    }
                }

                override fun onFailure(call: retrofit2.Call<EventDetailModel>, t: Throwable) {
                    _eventDetail.postValue(Result.Failure(t))
                }
            })
    }

    fun login(username: String, password: String) {
        _loginResult.postValue(Result.Loading)

        RetrofitClient.getInstance(context).login(username, password)
            .enqueue(object : retrofit2.Callback<BaseResponse<UserData>> {
                override fun onResponse(
                    call: retrofit2.Call<BaseResponse<UserData>>,
                    response: retrofit2.Response<BaseResponse<UserData>>
                ) {
                    if (!response.isSuccessful) {
                        _loginResult.postValue(Result.Failure(Exception("Failed: ${response.code()}")))
                        return
                    }
                    val body = response.body()
                    if (body != null) {
                        val data = body.data
                        if (data != null) {
                            _loginResult.postValue(Result.Success(data))
                        } else {
                            _loginResult.postValue(Result.Failure(Exception("No Data Found")))
                        }
                    } else {
                        _loginResult.postValue(Result.Failure(Exception("No Data Found")))
                    }
                }

                override fun onFailure(call: retrofit2.Call<BaseResponse<UserData>>, t: Throwable) {
                    _loginResult.postValue(Result.Failure(t))
                }
            })
    }

    fun logout(userId: Int, userToken: String) {
        _logoutResult.postValue(Result.Loading)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.getInstance(context).logoutUser(userId, userToken).execute()
                if (response.isSuccessful) {
                    _logoutResult.postValue(Result.Success(Unit))
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody.isNullOrEmpty()) {
                        _logoutResult.postValue(Result.Failure(Exception("Failed: ${response.code()} ${response.message()}")))
                    } else {
                        try {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            val message = errorResponse.message ?: "API Error"
                            _logoutResult.postValue(Result.Failure(Exception(message)))
                        } catch (e: Exception) {
                            _logoutResult.postValue(Result.Failure(Exception("Failed to parse error response")))
                        }
                    }
                }
            } catch (e: Exception) {
                _logoutResult.postValue(Result.Failure(e))
            }
        }
    }
}
