package com.vanshika.klf

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelLazy
import com.vanshika.klf.databinding.ActivityEventDetailBinding
import com.vanshika.klf.model.EventData
import com.vanshika.klf.model.EventDetailModel
import com.vanshika.klf.remote.EventViewModelFactory
import com.vanshika.klf.utils.Result
import com.vanshika.klf.utils.TokenManager
import com.vanshika.klf.viewmodel.EventViewModel


class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private val eventViewModel: Lazy<EventViewModel> by lazy {
        ViewModelLazy(
            EventViewModel::class.java.kotlin,
            { viewModelStore },
            { EventViewModelFactory(this) },
            { defaultViewModelCreationExtras }
        )
    }
    private var scanCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scanCountTv.text = scanCount.toString()

        val eventIdStr = intent.getStringExtra("event_id")
        val eventId = eventIdStr?.toIntOrNull() ?: -1

        if (eventId != -1) {
            binding.progressBar.visibility = View.VISIBLE
            eventViewModel.value.fetchEventDetail(eventId)
        }

        observeEventDetail()

        binding.btnScanQrCode.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SCAN)
        }

        binding.backImageView.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeEventDetail() {
        eventViewModel.value.eventDetail.observe(this, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val eventDetailModel = result.data
                    val params = eventDetailModel?.params
                    if (params?.get("user_token") == "Invalid parameter.") {
                        navigateToLogin()
                    } else if (eventDetailModel?.status == "success") {
                        eventDetailModel.let { updateUI(it) }
                    }
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
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

    private fun navigateToLogin() {
        TokenManager.clearToken(this)
        TokenManager.clearLoginState(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun updateUI(eventDetail: EventDetailModel) {
        val event: EventData? = eventDetail.data

        binding.eventTv.text = event?.title ?: "No Title Available"
        binding.scanCountTv.text = event?.metaData?.usedSlots ?: ("" + 0)
        binding.totalTv.text = event?.metaData?.totalSlots ?: ("" + 0)
        binding.startDateTv.text = "${event?.metaData?.startDate ?: ""} ${event?.metaData?.startTime ?: ""}"
        binding.endDateTv.text = "${event?.metaData?.endDate ?: ""} ${event?.metaData?.endTime ?: ""}"
        binding.addressTv.text = event?.metaData?.address ?: "No Address Available"
        binding.eventDisclaimerTv.text = event?.metaData?.disclaimer ?: "No Disclaimer Available"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            val scanned = data?.getBooleanExtra(SCANNED_RESULT, false) ?: false
            val scannedValue = data?.getStringExtra(SCANNED_VALUE)
            if (scanned) {
                incrementScanCount()
                println("Scanned QR Code: $scannedValue")
            }
        }
    }

    private fun incrementScanCount() {
        scanCount++
        binding.scanCountTv.text = scanCount.toString()
    }

    companion object {
        const val REQUEST_CODE_SCAN = 1
        const val SCANNED_RESULT = "scanned_result"
        const val SCANNED_VALUE = "scanned_value"
    }
}