package com.lib.cya.mvvm.view.toast

import android.content.Context
import android.widget.Toast

object ToastView {

    private var toast: Toast? = null

    fun show(context: Context, msg: String?, duration: Int = Toast.LENGTH_SHORT) {
        msg?.let {
            if (toast == null) {
                toast = Toast.makeText(context.applicationContext, it, Toast.LENGTH_SHORT)
            } else {
                toast?.setText(it)
                toast?.duration = duration
            }
            toast?.show()
        }
    }

}