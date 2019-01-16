package com.lib.cya.mvvm

import android.app.Activity
import android.app.Application
import com.lib.cya.mvvm.bean.AppInfo
import com.lib.cya.mvvm.bean.DisplayInfo
import com.lib.cya.mvvm.utils.ActivityTaskUtil
import com.lib.cya.mvvm.utils.DisplayUtil
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

object MVVMSDK {

    var taskMap = mutableMapOf<String, ArrayList<Activity>>()
    /**
     * APP信息，版本号等
     */
    lateinit var appinfo: AppInfo
    /**
     * 设备信息，包含像素
     */
    lateinit var displayInfo: DisplayInfo

    /**
     * 设置logger TAG
     */
    fun setLoggerTag(tag: String): MVVMSDK {
        val format = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag(tag).build()
        Logger.addLogAdapter(AndroidLogAdapter(format))
        return this
    }

    fun initApplication(application: Application) {
        appinfo = DisplayUtil.getAppInfo(application)
        displayInfo = DisplayUtil.getScreenSize(application)
    }

    /**
     * 用于保存所有页面
     */
    fun addSystemActivity(activity: Activity) {
        ActivityTaskUtil.addActivity(taskMap, ActivityTaskUtil.KEY_SYSTEM, activity)
    }

    /**
     * 退出app
     */
    fun exitSystem() {
        ActivityTaskUtil.cleanActivity(taskMap, ActivityTaskUtil.KEY_SYSTEM)
    }
}