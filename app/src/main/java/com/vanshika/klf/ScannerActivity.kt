package com.vanshika.klf

import android.content.DialogInterface
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.vanshika.klf.databinding.ActivityScannerBinding
import java.util.concurrent.Executors

class ScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScannerBinding
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startCamera()
            } else {
                showPermissionDeniedToast()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isCameraPermissionGranted) {
            startCamera()
        } else {
            requestCameraPermission()
        }
    }

    private val isCameraPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0

    private fun showPermissionDeniedToast() {
        Toast.makeText(this, "Camera permission denied. Please enable it from App Settings.", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")) {
            Toast.makeText(this, "Camera permission is required to scan QR codes. Please grant the permission.", Toast.LENGTH_SHORT).show()
            requestPermissionLauncher.launch("android.permission.CAMERA")
        } else {
            requestPermissionLauncher.launch("android.permission.CAMERA")
        }
    }

    private fun startCamera() {
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().apply {
                binding.previewView.surfaceProvider?.let { setSurfaceProvider(it) }
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .apply {
                    setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                        processImageProxy(imageProxy)
                    }
                }

            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)

        }, ContextCompat.getMainExecutor(this))
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            val scanner = BarcodeScanning.getClient()
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull()?.rawValue?.let { rawValue ->
                        runOnUiThread {
                            handleBarcode(rawValue)
                        }
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
        rawValue?.let {
            AlertDialog.Builder(this).apply {
                setTitle("Alert")
                setMessage("Click on open link to verify the QR.")
                setPositiveButton("Open Link") { _, _ ->
                    val intent = Intent("android.intent.action.VIEW")
                    startActivity(intent)
                    finish()
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    println("QR Code: $rawValue")
                    val intent = Intent()
                    intent.putExtra(EventDetailActivity.SCANNED_RESULT, true)
                    intent.putExtra(EventDetailActivity.SCANNED_VALUE, rawValue)
                    setResult(RESULT_OK, intent)
                    dialog.dismiss()
                    finish()
                }
                create().show()
            }
        }
    }
}