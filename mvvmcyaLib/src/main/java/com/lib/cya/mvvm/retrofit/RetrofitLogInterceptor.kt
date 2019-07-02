package com.lib.cya.mvvm.retrofit

import com.lcodecore.tkrefreshlayout.utils.LogUtil
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.internal.Util.UTF_8
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

class RetrofitLogInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(chain.request())
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        val mediaType = response.body()?.contentType()
        val content = response.body()?.string()
        Logger.e("请求地址：| $request")
        printParams(request.body())
        Logger.e("请求体返回：| Response:$content");
        Logger.e("----------请求耗时:" + duration + "毫秒----------")
        return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content
                ?: "")).build()
    }

    private fun printParams(body: RequestBody?) {
        val buffer = Buffer()
        try {
            body?.writeTo(buffer)
            var charset = Charset.forName("UTF-8")
            val contentType = body?.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF_8)
            }
            val params = buffer.readString(charset)
            LogUtil.i("请求参数： | $params")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}