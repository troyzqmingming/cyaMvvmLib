package com.lib.cya.mvvm.retrofit.callback

import com.lib.cya.mvvm.retrofit.exception.RetrofitThrowableInfo

interface IRetrofitSimpleVM {

    fun onThrowable(throwableInfo: RetrofitThrowableInfo)
}