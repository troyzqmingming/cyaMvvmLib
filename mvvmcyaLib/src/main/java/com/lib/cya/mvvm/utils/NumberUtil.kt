package com.lib.cya.mvvm.utils

import java.util.regex.Pattern

object NumberUtil {

    private const val REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$"

    fun isPhone(phoneString: String) = Pattern.matches(REGEX_MOBILE, phoneString)

    fun isPwd(pwdString: String): Boolean {
        //        判断密码是否包含数字：包含返回1，不包含返回0
        val i = if (pwdString.matches(".*\\d+.*".toRegex())) 1 else 0
        //        判断密码是否包含字母：包含返回1，不包含返回0
        val j = if (pwdString.matches(".*[a-zA-Z]+.*".toRegex())) 1 else 0
        //        判断密码是否包含特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)：包含返回1，不包含返回0
        val k = if (pwdString.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*".toRegex())) 1 else 0
        return i + j + k >= 2
    }
}
