package com.lib.cya.mvvm.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.DisplayMetrics
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.lib.cya.mvvm.bean.AppInfo
import com.lib.cya.mvvm.bean.DisplayInfo

object DisplayUtil {
    /**
     * 关闭软键盘
     */
    fun closeSoftKeyboard(activity: Activity) {
        val manager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(activity.window.decorView.windowToken,
                0)
    }

    fun openSoftKeyboard(activity: Activity) {
        val manager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 获取设备大小
     */
    fun getScreenSize(context: Context): DisplayInfo {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        return DisplayInfo(outMetrics.widthPixels, outMetrics.heightPixels)
    }

    /**
     * 获取app信息
     */
    fun getAppInfo(context: Context): AppInfo {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        val appInfo = AppInfo()
        appInfo.packageName = context.packageName
        appInfo.versionCode = packageInfo.versionCode.toString()
        appInfo.versionName = packageInfo.versionName
        return appInfo
    }

    fun callPhone(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val uri = Uri.parse("tel:$phone")
        intent.data = uri
        context.startActivity(intent)
    }

    fun sendSMS(context: Context, phone: String, msg: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phone"))
        intent.putExtra("sms_body", msg)
        context.startActivity(intent)
    }
}
