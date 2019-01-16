package com.lib.cya.mvvm.handler

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference

/**
 * 防止内存泄漏
 */
class SearchHandler(looper: Looper, messageHandler: MessageHandler) : Handler(looper) {


    interface MessageHandler {
        fun handlerMsg(msg: Message)
    }

    private var mMessageHandler = WeakReference<MessageHandler>(messageHandler)

    override fun handleMessage(msg: Message) {
        val realHandler = mMessageHandler.get()
        realHandler?.handlerMsg(msg)
    }

}