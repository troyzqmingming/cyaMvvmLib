package com.lib.cya.mvvm.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal class RetrofitGenerator private constructor() {

    var isDebug: Boolean = true

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitGenerator()
        }
    }

    fun build(baseUrl: String, header: Map<String, String>, maxRetryCount: Int = 0): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OKHttpGenerator.instance.setDebug(isDebug).genOKHttpClient(maxRetryCount, header))
                .build()
    }
}
