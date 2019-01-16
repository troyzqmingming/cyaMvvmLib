package com.lib.cya.mvvm.retrofit

import com.lib.cya.mvvm.retrofit.callback.IRetrofitBaseRequestCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 *
 * @param <R> result类型
 * @param <S> retorfit-interface
</I></R> */
class RetrofitManager private constructor(private val baseUrl: String, private val header: Map<String, String>, private val progress: IRetrofitProgressDialog?) {


    private constructor(builder: Builder) : this(
            builder.baseUrl,
            builder.header,
            builder.progress
    )

    companion object {
        fun build(init: Builder.() -> Unit) = Builder(init).build()
    }

    fun <R, S> doRequest(retrofitInterfaceClass: Class<S>, callback: IRetrofitBaseRequestCallback<R, S>): Disposable {
        val iService: S = RetrofitGenerator.instance.build(baseUrl, header).create(retrofitInterfaceClass)
        val call = callback.buildObservable(iService)
        return call
                .map { responseBody: ResponseBody ->
                    callback.onGotResponseBodySuccess(responseBody)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { disposable: Disposable ->
                    Unit
                    progress?.showDialog(disposable)
                }
                .subscribe({ r: R ->
                    progress?.dismissDialog()
                    callback.onGotSuccess(r)
                }, { t: Throwable ->
                    progress?.dismissDialog()
                    callback.onThrowable(t)
                })
    }


    /**
     * builder模式
     */
    class Builder private constructor() {
        internal lateinit var baseUrl: String
        internal var header: Map<String, String> = mapOf()
        internal var progress: IRetrofitProgressDialog? = null

        constructor(init: Builder.() -> Unit) : this() {
            init()
        }

        fun baseUrl(init: Builder.() -> String) = apply { baseUrl = init() }
        fun header(init: Builder.() -> Map<String, String>) = apply { header = init() }
        fun progress(init: Builder.() -> IRetrofitProgressDialog) = apply { progress = init() }
        fun build() = RetrofitManager(this)
    }
}
