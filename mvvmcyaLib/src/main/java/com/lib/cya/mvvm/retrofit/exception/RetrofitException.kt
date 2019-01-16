package com.lib.cya.mvvm.retrofit.exception

/**
 * @param errorCode ExceptionEngine.Error
 */
data class RetrofitException(var throwable: Throwable?, val errorCode: Int) : Exception() {

    lateinit var msg: String
    var httpCode: Int? = null
}