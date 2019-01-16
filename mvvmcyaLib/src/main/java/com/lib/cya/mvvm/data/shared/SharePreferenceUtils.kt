package com.lib.cya.mvvm.data.shared

import android.content.Context
import android.content.SharedPreferences

class SharePreferenceUtils private constructor(context: Context) {

    init {
        preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    companion object {
        private lateinit var preferences: SharedPreferences
        @Volatile
        private var instance: SharePreferenceUtils? = null

        fun getInstance(context: Context): SharePreferenceUtils = instance ?: synchronized(this) {
            instance ?: SharePreferenceUtils(context).also {
                instance = it
            }
        }
    }

    fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String? {
        return preferences.getString(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun putLong(key: String, defaultValue: Long) {
        preferences.edit().putLong(key, defaultValue).apply()
    }

    fun getLong(key: String, defaultValue: Long = 0): Long {
        return preferences.getLong(key, defaultValue)
    }

    fun putStringSet(key: String, value: HashSet<String>) {
        preferences.edit().putStringSet(key, value).apply()
    }

    fun getStringSet(key: String, defaultValue: HashSet<String> = hashSetOf()): Set<String>? {
        return preferences.getStringSet(key, defaultValue)
    }
}