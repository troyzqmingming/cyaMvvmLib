package com.lib.cya.mvvm

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * cannot access '<init>' it is 'public /*package*/' in 'TypeToken'
 * TypeToken无法直接创建，添加扩展函数
 * @param T list<Bean> 可直接返回列表
 */
inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)