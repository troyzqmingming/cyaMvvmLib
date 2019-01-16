package com.lib.cya.mvvm.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {

    /**
     * 是否wifi状态
     */
    fun isWifi(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return info != null && info.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 判断网络情况
     * @return false 表示没有网络 true 表示有网络
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        if (info != null) {
            return info.isAvailable
        }
        return false
    }
}