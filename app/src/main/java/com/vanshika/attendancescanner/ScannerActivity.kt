package com.vanshika.attendancescanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class ScannerActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startCamera()
            } else {
                showPermissionDeniedToast()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        previewView = findViewById(R.id.previewView)
        if (isCameraPermissionGranted()) {
            startCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun showPermissionDeniedToast() {
        Toast.makeText(
            this,
            "Camera permission denied. Please enable it from App Settings.",
            Toast.LENGTH_LONG
        ).show()
        finish() // Close the activity if permission is not granted
    }
    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(
                this,
                "Camera permission is required to scan QR codes. Please grant the permission.",
                Toast.LENGTH_LONG
            ).show()
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = androidx.camera.core.Preview.Builder().build()
            val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
            preview.setSurfaceProvider(previewView.surfaceProvider)
            val imageAnalysis = androidx.camera.core.ImageAnalysis.Builder()
                .setBackpressureStrategy(androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this)) { imageProxy ->
                processImageProxy(imageProxy)
            }

            cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageAnalysis
            )
        }, ContextCompat.getMainExecutor(this))
    }

    private fun processImageProxy(imageProxy: androidx.camera.core.ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val rawValue = barcode.rawValue
                        handleBarcode(rawValue)
                        break
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    private fun handleBarcode(rawValue: String?) {
        if (rawValue != null) {
            runOnUiThread {
                val builder = androidx.appcompat.app.AlertDialog.Builder(this)
                builder.setTitle("Open Link")
                builder.setMessage("Are you sure you want to open this link?\n\n$rawValue")
                builder.setPositiveButton("OK") { _, _ ->
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = android.net.Uri.parse(rawValue)
                    }
                    startActivity(intent)
                    finish()
                }
                builder.setNegativeButton("Cancel") { dialog, _ ->
                    println("QR Code: $rawValue")
                    val intent = Intent().apply {
                        putExtra(MainActivity.SCANNED_RESULT, true)
                        putExtra(MainActivity.SCANNED_VALUE, rawValue)
                    }
                    setResult(RESULT_OK, intent)
                    dialog.dismiss()
                    finish()
                }
                builder.create().show()
            }
        }
    }

}
