package com.lib.cya.mvvm.retrofit

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

internal class OKHttpGenerator private constructor() {

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OKHttpGenerator()
        }

        private const val CONNECT_TIMEOUT = 30//链接超时
        private const val READ_TIMEOUT = 30
        private const val WRITE_TIMEOUT = 30
    }

    private var isDebug: Boolean = true
    //header
    private var header: Map<String, Any> = hashMapOf()
    private lateinit var okHttpClient: OkHttpClient


    fun setDebug(isDebug: Boolean): OKHttpGenerator {
        this@OKHttpGenerator.isDebug = isDebug
        return this@OKHttpGenerator
    }

    internal fun genOKHttpClient(maxRetryCount: Int, headers: Map<String, Any>): OkHttpClient {
        this@OKHttpGenerator.header = headers
        this@OKHttpGenerator.okHttpClient = getOKHttp(maxRetryCount)
        return this@OKHttpGenerator.okHttpClient
    }

    private fun getLoggerInterceptor(): LoggingInterceptor {
        val builder = LoggingInterceptor.Builder()
        builder.loggable(isDebug)
        if (isDebug) {
            builder.setLevel(Level.BASIC)
            builder.log(Platform.INFO)
            builder.request("Request")
            builder.response("Response")
        }
        return builder.build()
    }

    private fun getHttpLogger(): HttpLoggingInterceptor {
        val builder = HttpLoggingInterceptor()
        builder.level = HttpLoggingInterceptor.Level.BODY
        return builder
    }

    private fun getOKHttp(maxRetryCount: Int): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(getLoggerInterceptor())
                .addInterceptor(Retry(maxRetryCount))
                .addInterceptor { chain: Interceptor.Chain? ->
                    val builder = chain!!.request().newBuilder()
                    val keys = header.keys
                    for (key in keys) {
                        builder.addHeader(key, header.getValue(key).toString())
                    }
                    chain.proceed(builder.build())
                }
                .build()
    }

    class Retry(private val maxRetryCount: Int = 0/*重试次数*/) : Interceptor {


        override fun intercept(chain: Interceptor.Chain?): Response {
            val request = chain!!.request()
//            Logger.e("url:${request.url()}")
            var response = chain.proceed(request)
            var curRetry = 0
            while (response!!.isSuccessful && curRetry < maxRetryCount) {
                response = chain.proceed(request)
                curRetry++
            }
            return response
        }

    }
}
