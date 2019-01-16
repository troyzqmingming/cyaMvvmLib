package com.lib.cya.mvvm.utils

import android.app.Activity

object ActivityTaskUtil {

    const val KEY_SYSTEM: String = "key_system"

    fun addActivity(map: MutableMap<String, ArrayList<Activity>>, key: String, activity: Activity) {
        var list: ArrayList<Activity>
        if (map.containsKey(key)) {
            list = map[key]!!
        } else {
            list = arrayListOf()
            map[key] = list
        }
        list.add(activity)
    }

    fun cleanActivity(map: MutableMap<String, ArrayList<Activity>>, key: String) {
        map[key]?.let {
            for (activity in it) {
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
            it.clear()
        }
    }

}

