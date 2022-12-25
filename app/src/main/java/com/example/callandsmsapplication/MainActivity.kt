package com.example.callandsmsapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.callandsmsapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val CALL_PERMISSION_CODE = 100
        private const val SMS_PERMISSION_CODE = 101
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onclick()
    }

    private fun onclick() {
        call()
        sms()
    }

    private fun call() {
        binding.callbtn?.setOnClickListener {
            checkPermission(
                Manifest.permission.CALL_PHONE,
                CALL_PERMISSION_CODE
            )
        }
    }

    private fun checkPermission(callPhone: String, callPermissionCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                callPhone
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(callPhone), callPermissionCode)
        } else {
            makecall()

            Toast.makeText(this, R.string.permissionalready, Toast.LENGTH_SHORT).show()
        }

    }

    private fun sms() {
        binding.smsbtn?.setOnClickListener {
            checkPermissionsms(
                Manifest.permission.SEND_SMS,
                SMS_PERMISSION_CODE
            )
        }
    }

    private fun checkPermissionsms(sms: String, smsPermissionCode: Int) {
        if (ContextCompat.checkSelfPermission(this, sms) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(sms), smsPermissionCode)

        } else {
            sendsms()
            Toast.makeText(this, R.string.permissionalready, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, R.string.grantcallpermission, Toast.LENGTH_SHORT)
                    .show()
                makecall()
            } else {
                dialog(this).showPermisssionDeniedDialog(
                    getString(R.string.call), getString(R.string.gotosetting),
                    CALL_PERMISSION_CODE
                )
                Toast.makeText(this@MainActivity, R.string.callpermissiondenied, Toast.LENGTH_SHORT)
                    .show()
            }
        } else if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.grantsmspermission, Toast.LENGTH_SHORT).show()
                sendsms()
            } else {
                dialog(this).showPermisssionDeniedDialog(
                    getString(R.string.sms), getString(R.string.gotosetting),
                    SMS_PERMISSION_CODE
                )
                Toast.makeText(this, R.string.smspermissiondenied, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendsms() {
        val phoneNumber = binding.inputnumber.text.toString()
        val message = binding.smsedit.text.toString()
        try {
            val smsManager: SmsManager
            if (Build.VERSION.SDK_INT>=23) {
                smsManager = this.getSystemService(SmsManager::class.java)
            }else{
                smsManager = SmsManager.getDefault()
            }
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this,R.string.massagesend, Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception) {
            Toast.makeText(this,"problem", Toast.LENGTH_SHORT).show()
        }
    }
    private fun makecall() {
        val phone_number: String = binding.inputnumber.text.toString()
        val phone_intent = Intent(Intent.ACTION_CALL)
        phone_intent.data = Uri.parse("tel:$phone_number")
        startActivity(phone_intent)
    }
}