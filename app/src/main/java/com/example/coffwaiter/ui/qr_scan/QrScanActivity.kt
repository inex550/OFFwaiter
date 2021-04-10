package com.example.coffwaiter.ui.qr_scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.coffwaiter.R
import com.example.coffwaiter.dialogs.InfoDialog
import com.example.coffwaiter.ui.restaurant.RestaurantActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QrScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var cameraGranted = false

    lateinit var scannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)

        scannerView.setLaserEnabled(false)
        scannerView.setBorderColor(R.color.white)
        scannerView.setFormats(listOf(BarcodeFormat.QR_CODE))
        setContentView(scannerView)


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            scannerView.setResultHandler(this)
            scannerView.startCamera()

            cameraGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraGranted = true

                scannerView.setResultHandler(this)
                scannerView.startCamera()
            } else InfoDialog(message = "Для сканирования QR-кода необходим доступ к камере") {
                finish()
            }.show(supportFragmentManager, null)
        }
    }

    override fun onResume() {
        super.onResume()
        if (cameraGranted) {
            scannerView.setResultHandler(this)
            scannerView.startCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

    override fun handleResult(result: Result) {
        val openRestaurantIntent = Intent(this, RestaurantActivity::class.java)
        openRestaurantIntent.putExtra("rid", result.text)
        startActivity(openRestaurantIntent)
    }
}