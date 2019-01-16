package com.lib.cya.mvvm.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateUtil {

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(curTime: Long = System.currentTimeMillis(), format: String = "yyyy-MM-dd HH:mm:ss"): String = SimpleDateFormat(format).format(curTime)

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(dateString: String,format: String): Long {
        return SimpleDateFormat(format).parse(dateString).time
    }
}