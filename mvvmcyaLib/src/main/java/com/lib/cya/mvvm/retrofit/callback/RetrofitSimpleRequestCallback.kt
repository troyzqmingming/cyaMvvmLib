package com.lib.cya.mvvm.retrofit.callback

import android.content.Context
import com.lib.cya.mvvm.retrofit.exception.ExceptionEngine
import com.lib.cya.mvvm.retrofit.exception.RetrofitThrowableInfo
import com.lib.cya.mvvm.view.toast.ToastView
import io.reactivex.disposables.Disposable

/**
 * 此回调已简单处理onThrowable
 */
abstract class RetrofitSimpleRequestCallback<R, S>(val context: Context) : IRetrofitBaseRequestCallback<R, S> {

    /**
     * 接收throw
     */
    var retrofitThrowableVM: IRetrofitSimpleVM? = null

    override fun onThrowable(throwable: Throwable) {
        val exception = ExceptionEngine.handleException(throwable)
        when (exception.errorCode) {
            ExceptionEngine.Error.HTTP_ERROR -> {
                retrofitThrowableVM?.onThrowable(RetrofitThrowableInfo(exception.errorCode, exception.msg, exception.httpCode))
            }
            ExceptionEngine.Error.NETWORK_ERROR -> {
                ToastView.show(context, "请检测您的网络")
                retrofitThrowableVM?.onThrowable(RetrofitThrowableInfo(exception.errorCode, exception.msg))
                return
            }
            ExceptionEngine.Error.PARSE_ERROR -> {
                ToastView.show(context, "解析错误")
                retrofitThrowableVM?.onThrowable(RetrofitThrowableInfo(exception.errorCode, exception.msg))
            }
            ExceptionEngine.Error.UNKNOWN -> {
                retrofitThrowableVM?.onThrowable(RetrofitThrowableInfo(exception.errorCode, exception.msg))
            }
        }
    }

}