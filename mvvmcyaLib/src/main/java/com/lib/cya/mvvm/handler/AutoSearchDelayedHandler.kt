package com.lib.cya.mvvm.handler

import android.os.Looper
import android.os.Message

/**
 * 输入自动搜索,防止输入过快
 */
class AutoSearchDelayedHandler(looper: Looper, private val delayMills: Long = 500L) : SearchHandler.MessageHandler {

    interface IFinishListener {
        fun getKeyword(keyword: String)
    }

    private val mHandler = SearchHandler(looper, this)
    private val myRunnable = MyRunnable(mHandler)
    private var currentWord: String? = null
    /**
     * 必须实现，并在此接口使用搜索
     */
    var listener: IFinishListener? = null

    /**
     * EditText中的文字发生改变时，将handle中的Callback移除
     * 然后使用Handle发一个延时的消息。最后通过回调getKeyword，让Activity开始搜索
     */
    fun optionSearch(keyword: String) {
        this.currentWord = keyword
        mHandler.removeCallbacks(myRunnable)
        mHandler.postDelayed(myRunnable, delayMills)
    }

    override fun handlerMsg(msg: Message) {
        currentWord?.let {
            listener?.getKeyword(it)
        }

    }


    private class MyRunnable(val mHandler: SearchHandler) : Runnable {
        override fun run() {
            mHandler.sendEmptyMessage(1)
        }

    }
}