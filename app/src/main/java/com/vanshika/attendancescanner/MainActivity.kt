package com.vanshika.attendancescanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var scanCount = 0
    private lateinit var scanCountTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scanCountTv = findViewById(R.id.scan_count_tv)
        val scanButton: Button = findViewById(R.id.btn_scan_qr_code)

        scanCountTv.text = scanCount.toString()

        scanButton.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SCAN)
        }
        val recyclerView: RecyclerView = findViewById(R.id.attendanceRecyclerView)

        val items = listOf(
            AttendanceItem("John", "Doe", "10", "A"),
            AttendanceItem("Jane", "Smith", "11", "B"),
            AttendanceItem("Michael", "Brown", "9", "C"),
            AttendanceItem("Emily", "Davis", "12", "D"),
            AttendanceItem("Chris", "Wilson", "8", "E"),
            AttendanceItem("John", "Doe", "10", "A"),
            AttendanceItem("Jane", "Smith", "11", "B"),
            AttendanceItem("Michael", "Brown", "9", "C"),
            AttendanceItem("Emily", "Davis", "12", "D"),
            AttendanceItem("Chris", "Wilson", "8", "E")
        )

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AttendanceAdapter(items)
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
        scanCountTv.text = scanCount.toString()
    }

    companion object {
        const val REQUEST_CODE_SCAN = 1
        const val SCANNED_RESULT = "scanned_result"
        const val SCANNED_VALUE = "scanned_value"
    }
}
