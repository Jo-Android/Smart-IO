package com.hgtech.smartio.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.hgtech.smartio.R
import com.hgtech.smartio.ui.custom.setupConfirmDialog


class BarCodeScannerActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    var requestCameraPermission: ActivityResultLauncher<Array<String>>? = null
    lateinit var cameraPermissionResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_code_scanner)


        requestCameraPermission =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                permissions.entries.forEach {
                    if (it.value) {
                        if (it.key == "android.permission.CAMERA") {
                            codeScanner.startPreview()
                        }
                    } else
                        askForPermissionGrant(
                            getString(R.string.ask_camera_permission)
                        )
                }
            }
        requestCameraPermissions()
        cameraPermissionResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
                requestCameraPermissions()
            }

        val scannerView = findViewById<CodeScannerView>(R.id.scanner)

        codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val returnIntent = Intent()
                returnIntent.putExtra(QR_SCANNER, it.text)
                returnIntent.putExtra(ITEM_SELECTED, intent.getStringExtra(ITEM_SELECTED))
                setResult(RESULT_OK, returnIntent)
                finish()
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            requestCameraPermissions()
        }
    }

    private fun requestCameraPermissions() {
        requestCameraPermission?.launch(
            arrayOf(
                Manifest.permission.CAMERA,
            )
        )
    }

    override fun onResume() {
        super.onResume()
        requestCameraPermissions()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    companion object {
        const val QR_SCANNER = "Qr Code"
        const val ITEM_SELECTED = "Item"
    }

    private fun askForPermissionGrant(txt: String) {
        setupConfirmDialog(
            this,
            txt,
            "Enable",
            0,
            "Cancel"
        ) { isConfirm: Boolean, _ ->
            if (isConfirm) {
                cameraPermissionResultLauncher.launch(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", this.packageName, null),
                    )
                )
            }
        }
    }
}