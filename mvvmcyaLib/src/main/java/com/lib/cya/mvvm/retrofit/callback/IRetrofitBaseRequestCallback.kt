package com.lib.cya.mvvm.retrofit.callback

import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * R :Result
 * S :RetrofitInterface
 */
interface IRetrofitBaseRequestCallback<R, S> {

    fun buildObservable(retrofitInterface: S): Observable<ResponseBody>

    fun buildFlowable(retrofitInterface: S): Flowable<ResponseBody>?

    fun onGotResponseBodySuccess(response: ResponseBody): R
    fun onGotSuccess(result: R)
    /**
     * 可使用exceptionEngine
     */
    fun onThrowable(throwable: Throwable)

    fun onCancel()
}
