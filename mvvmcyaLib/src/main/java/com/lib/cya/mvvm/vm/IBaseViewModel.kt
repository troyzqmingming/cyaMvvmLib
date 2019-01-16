package com.lib.cya.mvvm.vm

import io.reactivex.disposables.Disposable

interface IBaseViewModel{

    fun onCreate()

    fun onDestroy()

    fun registerRxBus()

    fun removeRxBus()

    fun loadWithoutNet()

    /**
     * 取消正在进行的请求
     */
    fun cancelDisposable(): Array<Disposable?>?
}