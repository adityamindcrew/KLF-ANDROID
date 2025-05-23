package com.vanshika.klf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vanshika.klf.databinding.ActivityEventListBinding
import com.vanshika.klf.model.Event
import com.vanshika.klf.remote.EventViewModelFactory
import com.vanshika.klf.utils.DateUtils
import com.vanshika.klf.utils.Result
import com.vanshika.klf.utils.TokenManager
import com.vanshika.klf.viewmodel.EventViewModel
import java.util.ArrayList
import java.util.Date
import kotlin.Unit
import kotlin.collections.emptyList
import kotlin.collections.toList
import kotlin.collections.toMutableList
import kotlin.jvm.functions.Function0


class EventListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventListBinding
    private val eventViewModel: Lazy<EventViewModel> by lazy {
        ViewModelLazy(
            EventViewModel::class.java.kotlin, // Correct way to get KClass from Class
            { viewModelStore },
            { EventViewModelFactory(this) },
            { defaultViewModelCreationExtras } // Or a custom CreationExtras if needed
        )
    }
    private var eventList: MutableList<Event> = ArrayList()
    private var selectedStartDate: Date? = null
    private var selectedEndDate: Date? = null
    private var searchKey: String? = null
    private lateinit var eventAdapter: EventAdapter
    private var isLoading: Boolean = false
    private var currentPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        observeEventList()
        observeLogout()
        fetchEventData(reset = false)
    }

    private fun initUI() {
        binding.btnStartDate.setOnClickListener {
            DateUtils.showDatePicker(this, true, selectedStartDate, selectedEndDate) { date ->
                selectedStartDate = date
                binding.btnStartDate.text = DateUtils.getFormattedDate(date, false)
                Unit
            }
        }

        binding.btnEndDate.setOnClickListener {
            currentPage = 0
            DateUtils.showDatePicker(this, false, selectedStartDate, selectedEndDate) { date ->
                selectedEndDate = date
                binding.btnEndDate.text = DateUtils.getFormattedDate(date, false)
                binding.clearImageView.visibility = View.VISIBLE
                fetchEventData(reset = false)
                Unit
            }
        }

        binding.clearImageView.setOnClickListener {
            selectedStartDate = null
            selectedEndDate = null
            binding.btnStartDate.text = "Start Date"
            binding.btnEndDate.text = "End Date"
            binding.clearImageView.visibility = View.GONE
            fetchEventData(reset = false)
        }

        binding.ivMenu.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.ivMenu)
            menuInflater.inflate(R.menu.menu_logout, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                if (item.itemId == R.id.logout) {
                    logout(this)
                    true
                } else {
                    false
                }
            }
            popupMenu.show()
        }

        binding.etSearch.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == 3 || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                val newSearchKey = binding.etSearch.text.toString().trim()
                if (searchKey == newSearchKey) {
                    true
                } else {
                    searchKey = newSearchKey
                    hideKeyboard()
                    fetchEventData(reset = true)
                    true
                }
            } else {
                false
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            clearFilters()
            binding.clearImageView.visibility = View.GONE
        }

        binding.textInputSearch.setEndIconOnClickListener {
            currentPage = 0
            clearFilters()
        }

        eventAdapter = EventAdapter { event ->
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("event_id", event.id)
            startActivity(intent)
            Unit
        }
        binding.rvDetails.layoutManager = GridLayoutManager(this, 2)
        binding.rvDetails.adapter = eventAdapter
        binding.rvDetails.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && dy > 0 && totalItemCount > 0 && visibleItemCount + firstVisibleItemPosition >= totalItemCount - 5) {
                    binding.progressCircular.visibility = View.VISIBLE
                    fetchEventData(reset = false)
                }
            }
        })
    }

    private fun fetchEventData(reset: Boolean) {
        if (isLoading) {
            return
        }
        isLoading = true
        binding.progressCircular.visibility = View.VISIBLE
        hideKeyboard()
        if (reset) {
            currentPage = 1
            eventList.clear()
            eventAdapter.submitList(emptyList())
        }
        eventViewModel.value.fetchEvents(
            DateUtils.getFormattedDate(selectedStartDate, true),
            DateUtils.getFormattedDate(selectedEndDate, true),
            searchKey,
            null,
            currentPage
        )
    }

    private fun observeEventList() {
        eventViewModel.value.eventList.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    isLoading = true
                }
                is Result.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    isLoading = false
                    val eventListModel = result.data
                    Log.d("EVENT_LIST_DEBUG", "Params: ${eventListModel?.params}")

                    if (eventListModel?.params?.get("user_token") == "Invalid parameter.") {
                        Handler(Looper.getMainLooper()).post { navigateToLogin() }
                        return@Observer
                    }

                    val data = eventListModel?.data?.values?.toList() ?: emptyList()
                    if (selectedStartDate != null && selectedEndDate != null) {
                        if (data.isNotEmpty()) {
                            if (currentPage == 1) {
                                eventList = data.toMutableList()
                            } else {
                                eventList.addAll(data)
                            }
                            eventAdapter.submitList(eventList.toList())
                            currentPage++
                        } else {
                            eventList.clear()
                            eventAdapter.submitList(emptyList())
                            Toast.makeText(this, "No events found for the selected date range", Toast.LENGTH_SHORT).show()
                        }
                    } else if (data.isNotEmpty()) {
                        if (currentPage == 1) {
                            eventList = data.toMutableList()
                        } else {
                            eventList.addAll(data)
                        }
                        eventAdapter.submitList(eventList.toList())
                        currentPage++
                    }
                }
                is Result.Failure -> {
                    binding.progressCircular.visibility = View.GONE
                    isLoading = false
                    val message = result.exception.message ?: "Unknown error"
                    Log.e("EVENT_LIST_ERROR", "Error fetching events: $message")
                    if (message == "Invalid parameter(s): user_token") {
                        Log.d("EVENT_LIST_DEBUG", "User token is invalid. Navigating to login...")
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(this, "Session expired. Redirecting to login.", Toast.LENGTH_SHORT).show()
                            navigateToLogin()
                        }
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun clearFilters() {
        hideKeyboard()
        selectedStartDate = null
        selectedEndDate = null
        searchKey = ""
        binding.etSearch.text?.clear()
        binding.btnStartDate.text = "Start Date"
        binding.btnEndDate.text = "End Date"
        fetchEventData(true)
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun checkTokenAndRedirect(context: Context) {
        if (TokenManager.isTokenExpired(context)) {
            TokenManager.clearToken(context)
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    private fun navigateToLogin() {
        TokenManager.clearToken(this)
        TokenManager.clearLoginState(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun logout(context: Context) {
        Log.d("Logout", "Logout process started")
        val userId = TokenManager.getUserId(context)
        val userToken = TokenManager.getToken(context)
        Log.d("Logout", "User ID: $userId, User Token: $userToken")
        if (userId != null && userToken != null) {
            Log.d("Logout", "Making API call to logout user")
            eventViewModel.value.logoutApiCall(userId, userToken)
        } else {
            Log.e("Logout", "User ID or Token is null, cannot proceed with logout")
            // Optionally, navigate to login immediately if token is missing locally
            navigateToLogin()
        }
    }

    private fun observeLogout() {
        eventViewModel.value.logoutResult.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    Log.d("Logout", "Loading state: Showing progress")
                    binding.progressCircular.visibility = View.VISIBLE
                    isLoading = true
                }
                is Result.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    navigateToLogin()
                    Log.d("Logout", "Navigating to LoginActivity after successful logout")
                }
                is Result.Failure -> {
                    // Even on failure, if the server indicates logout, we might clear local data and go to login
                    // This depends on the desired user experience for logout errors.
                    navigateToLogin()
                    binding.progressCircular.visibility = View.GONE
                    Log.e("Logout", "Logout failed: ${result.exception.message}")
                }
            }
        })
    }
}