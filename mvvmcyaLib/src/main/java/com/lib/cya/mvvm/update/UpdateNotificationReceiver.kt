package com.lib.cya.mvvm.update

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.io.File

class UpdateNotificationReceiver : BroadcastReceiver() {

    companion object {

        const val UPDATE_FILE_PATH = "update_file_path"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        val filePath = intent?.getStringExtra(UPDATE_FILE_PATH)
        filePath?.let {
            UpdateManager.install(context, File(it))
        }

    }

}