package com.lib.cya.mvvm.retrofit.exception

/**
 * 返回至vm
 * @param errorCode ExceptionEngine.Error
 */
data class RetrofitThrowableInfo(val errorCode: Int, val msg: String, val httpCode: Int? = 0)