package com.lib.cya.mvvm.retrofit.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

object ExceptionEngine {

    //对应HTTP的状态码
    const val HTTP_400 = 400
    const val HTTP_UNAUTHORIZED = 401
    const val HTTP_FORBIDDEN = 403
    const val HTTP_NOT_FOUND = 404
    const val HTTP_REQUEST_TIMEOUT = 408
    const val HTTP_INTERNAL_SERVER_ERROR = 500
    const val HTTP_BAD_GATEWAY = 502
    const val HTTP_SERVICE_UNAVAILABLE = 503
    const val HTTP_GATEWAY_TIMEOUT = 504


    fun handleException(t: Throwable?): RetrofitException {
        var retrofitException: RetrofitException
        when (t) {
            is HttpException -> {
                retrofitException = RetrofitException(t, Error.HTTP_ERROR)
                when (t.code()) {
                    HTTP_400 -> {
                        retrofitException.msg = "请检测参数" //均视为网络错误
                        retrofitException.httpCode = t.code()
                    }
                    HTTP_UNAUTHORIZED,
                    HTTP_FORBIDDEN,
                    HTTP_NOT_FOUND,
                    HTTP_REQUEST_TIMEOUT,
                    HTTP_GATEWAY_TIMEOUT,
                    HTTP_INTERNAL_SERVER_ERROR,
                    HTTP_BAD_GATEWAY,
                    HTTP_SERVICE_UNAVAILABLE -> {
                        retrofitException.msg = "network is unavailable" //均视为网络错误
                        retrofitException.httpCode = t.code()
                    }
                }
            }
            is JsonParseException, is JSONException, is ParseException -> {
                //均视为解析错误
                retrofitException = RetrofitException(t, Error.PARSE_ERROR)
                retrofitException.msg = "parse error"
            }
            is ConnectException, is UnknownHostException -> {
                //均视为网络错误
                retrofitException = RetrofitException(t, Error.NETWORK_ERROR)
                retrofitException.msg = "network error"
            }
            else -> {
                retrofitException = RetrofitException(t, Error.UNKNOWN)
                retrofitException.msg = "unknown error"
            }
        }
        return retrofitException
    }

    object Error {
        /**
         * 未知错误
         */
        const val UNKNOWN = 1000
        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001
        /**
         * 网络错误
         */
        const val NETWORK_ERROR = 1002
        /**
         * 协议出错
         */
        const val HTTP_ERROR = 1003
    }
}