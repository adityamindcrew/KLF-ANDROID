package com.vanshika.klf.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vanshika.klf.model.EventDetailModel
import com.vanshika.klf.model.EventListModel
import com.vanshika.klf.model.UserData
import com.vanshika.klf.remote.EventRepository
import com.vanshika.klf.utils.Result
import kotlin.Unit

class EventViewModel(context: Context) : ViewModel() {

    private val repository = EventRepository(context)

    val eventList: LiveData<Result<EventListModel>> get() = repository.eventList
    val eventDetail: LiveData<Result<EventDetailModel>> get() = repository.eventDetail
    val loginResult: LiveData<Result<UserData>> get() = repository.loginResult
    val logoutResult: LiveData<Result<Unit>> get() = repository.logoutResult

    fun fetchEvents(
        fromDate: String? = null,
        toDate: String? = null,
        searchKey: String? = null,
        signupType: String? = null,
        currentPage: Int = 0
    ) {
        repository.fetchEvents(fromDate, toDate, searchKey, signupType, currentPage)
    }

    fun fetchEventDetail(eventId: Int) {
        repository.fetchEventDetail(eventId)
    }

    fun login(username: String, password: String) {
        repository.login(username, password)
    }

    fun logoutApiCall(userId: Int, userToken: String) {
        repository.logout(userId, userToken)
    }
}
