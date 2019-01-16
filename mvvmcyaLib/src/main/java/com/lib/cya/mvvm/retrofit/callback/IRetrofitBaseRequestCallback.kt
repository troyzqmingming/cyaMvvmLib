package com.lib.cya.mvvm.retrofit.callback

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody

/**
 * R :Result
 * S :RetrofitInterface
 */
interface IRetrofitBaseRequestCallback<R, S> {

    fun buildObservable(retrofitInterface: S): Observable<ResponseBody>
    fun onGotResponseBodySuccess(response: ResponseBody): R
    fun onGotSuccess(result: R)
    /**
     * 可使用exceptionEngine
     */
    fun onThrowable(throwable: Throwable)
}
