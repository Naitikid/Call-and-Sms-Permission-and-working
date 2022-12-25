package com.example.callandsmsapplication
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class dialog(val activity: Activity) {

    fun showPermisssionDeniedDialog(title: String, message: String, requestcode: Int) {
        val builder = MaterialAlertDialogBuilder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setMessage("Setting")
        builder.setNegativeButton("Go To Setting") { _, _ ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            activity.startActivityIfNeeded(intent, requestcode)
        }
        builder.setPositiveButton("No") { cancel, _ ->
            cancel.dismiss()
        }
        builder.create()
        builder.show()
    }
}
