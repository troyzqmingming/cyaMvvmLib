package com.lib.cya.mvvm.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import java.io.File

/**
 * 用于缓存数据至文件
 */
object FileCacheUtil {

    /**
     * 把实体转换为json保存至文件
     */
    fun <R> writeFile(filePath: String, fileName: String, value: R) {
        val jsonString = Gson().toJson(value)
        if (TextUtils.isEmpty(jsonString)) {
            return
        }
        val dirs = File(filePath)
        if (!dirs.exists()) {
            dirs.mkdirs()
            Logger.d("创建文件目录:{$dirs}")
        }
        val cacheFile = File(filePath + "/" + File.separator + fileName)
        if (!cacheFile.exists()) {
            cacheFile.createNewFile()
            Logger.d("创建文件:{$cacheFile}")
        }
        cacheFile.writeText(jsonString)
    }

    /**
     * 读取文件缓存
     */
    fun <R> readFile(filePath: String, fileName: String, resultType: Class<R>): R? {
        val cacheFile = File(filePath + "/" + File.separator + fileName)
        if (cacheFile.exists()) {
            val cacheValue = cacheFile.readText()
            Logger.d("${fileName}缓存读取:{$cacheFile}")
            //适用于list、bean
//            val turnsType = object : TypeToken<R>() {}.type
//            return Gson().fromJson<R>(cacheValue, turnsType)
            return Gson().fromJson(cacheValue, resultType)
        }
        Logger.d("${fileName}无缓存读取")
        return null
    }

}