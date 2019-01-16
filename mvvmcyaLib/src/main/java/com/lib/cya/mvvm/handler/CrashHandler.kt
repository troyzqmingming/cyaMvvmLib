package com.lib.cya.mvvm.handler

import android.content.Context
import android.os.Build
import android.os.Looper
import android.os.SystemClock
import com.lib.cya.mvvm.utils.DateUtil
import com.lib.cya.mvvm.utils.DisplayUtil
import com.lib.cya.mvvm.view.toast.ToastView
import com.orhanobut.logger.Logger
import java.io.File
import java.io.PrintWriter
import java.io.RandomAccessFile
import java.io.StringWriter

class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    interface CrashCallback {
        fun crash()
    }

    companion object {
        val instance: CrashHandler by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            CrashHandler()
        }
    }

    private lateinit var crashCallback: CrashCallback
    private lateinit var filePath: String
    private lateinit var fileName: String
    private lateinit var mDefaultHandler: Thread.UncaughtExceptionHandler
    private lateinit var context: Context
    private val crashMap = hashMapOf<String, String>()


    fun init(context: Context, filePath: String, fileName: String, crashCallback: CrashCallback) {
        this@CrashHandler.context = context
        this@CrashHandler.filePath = filePath
        this@CrashHandler.fileName = fileName
        this@CrashHandler.crashCallback = crashCallback
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    private fun handleException(throwable: Throwable?): Boolean {
        throwable?.let {
            Thread {
                Looper.prepare()
                ToastView.show(context, "很抱歉,程序出现异常,即将退出..")
                Looper.loop()
            }.start()
            // 收集设备参数信息
            collectDeviceInfo(context)
            // 保存日志文件
            saveCrashInfoFile(throwable)
            SystemClock.sleep(3000L)
            return true
        }
        return false
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        if (!handleException(e)) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(t, e)
        } else {
            SystemClock.sleep(3000L)
            crashCallback.crash()
        }
    }

    private fun collectDeviceInfo(context: Context) {
        val info = DisplayUtil.getAppInfo(context)
        crashMap["versionName"] = info.versionName
        crashMap["versionCode"] = info.versionCode
        val fields = Build::class.java.declaredFields
        for (index in fields) {
            index.isAccessible = true
            crashMap[index.name] = index.get(null).toString()
        }
    }

    private fun saveCrashInfoFile(throwable: Throwable): String {
        val buffer = StringBuffer()
        val dateString = DateUtil.dateFormat()
        buffer.append("\r\n" + dateString + "\n")
        for (index in crashMap.entries) {
            buffer.append(index.key + "-" + index.value + "\n")
        }
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        throwable.printStackTrace(printWriter)
        var cause = throwable.cause
        cause?.let {
            it.printStackTrace(printWriter)
            cause = it.cause
        }
        printWriter.flush()
        printWriter.close()
        val result = writer.toString()
        buffer.append(result)
        Logger.d(buffer.toString())
        //write file
        return writeFile(filePath, fileName, buffer.toString())
    }

    private fun writeFile(filePath: String, fileName: String, msg: String): String {
        val fileDir = File(filePath)
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }
        val crashFile = File(fileDir.path, "/$fileName")
        if (!crashFile.exists()) {
            crashFile.createNewFile()
        }
        val randomFile = RandomAccessFile(filePath + fileName, "rw")
        val fileLength = randomFile.length()
        randomFile.seek(fileLength)
        randomFile.writeBytes(msg)
        randomFile.close()
        return fileName
    }
}