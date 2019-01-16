package com.lib.cya.mvvm.utils

import android.content.Context
import android.util.TypedValue

object DesignUtil {

    /**
     * dp转px
     */
    fun dp2px(context: Context, dp: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()

    /**
     * px2dp
     */
    fun px2dp(context: Context, px: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px.toFloat(), context.resources.displayMetrics).toInt()


}