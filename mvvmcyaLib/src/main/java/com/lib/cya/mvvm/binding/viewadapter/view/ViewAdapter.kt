package com.lib.cya.mvvm.binding.viewadapter.view

import android.databinding.BindingAdapter
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.lib.cya.mvvm.binding.viewadapter.command.BindingCommand
import java.util.concurrent.TimeUnit

open class ViewAdapter {

    companion object {

        /**
         * requireAll 是意思是是否需要绑定全部参数
         * onClickCommand 绑定点击命令
         * clickDelayed 是否开启防止点击过快,默认不能过快点击
         */
        @BindingAdapter(value = ["onClickCommand", "clickDelayed"], requireAll = false)
        @JvmStatic
        fun onClickCommand(view: View, bindingCommand: BindingCommand, clickDelayed: Boolean = true) {
            if (clickDelayed) {
                RxView.clicks(view)
                        .throttleFirst(1, TimeUnit.SECONDS).subscribe {
                            bindingCommand.execute()
                        }
            } else {
                RxView.clicks(view)
                        .subscribe {
                            bindingCommand.execute()
                        }
            }
        }
    }
}