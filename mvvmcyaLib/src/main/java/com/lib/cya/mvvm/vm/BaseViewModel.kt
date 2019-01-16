package com.lib.cya.mvvm.vm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.hwangjr.rxbus.RxBus
import com.lib.cya.mvvm.ui.BaseActivity
import com.lib.cya.mvvm.view.toast.ToastView
import io.reactivex.disposables.Disposable

open class BaseViewModel(val activity: BaseActivity<*, *>) : IBaseViewModel {

    override fun cancelDisposable(): Array<Disposable?>? {
        return null
    }

    override fun loadWithoutNet() {
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun registerRxBus() {
        RxBus.get().register(this)
    }

    override fun removeRxBus() {
        RxBus.get().unregister(this)
    }

    fun <A : Activity> jumpActivity(toClass: Class<A>, action: String?, bundle: Bundle = Bundle()) {
        val intent = Intent(activity, toClass)
        intent.putExtras(bundle)
        action?.let {
            intent.action = action
        }
        activity.startActivity(intent)
    }

    fun show(string: String) {
        ToastView.show(activity, string)
    }

    fun finish() {
        if (!activity.isFinishing) {
            activity.finish()
        }
    }
}