package com.tesla.framework.common.util

import android.os.Build

/**
 * Created by Jerry on 2021/10/18.
 */
class DrakeetUtil {
    fun isAndroid12():Boolean{
        //Build.VERSION.PREVIEW_SDK_INT > 0 不要写在 Build.VERSION.SDK_INT == 30 前面，因为 6.0以下没有这个常量
        return (Build.VERSION.SDK_INT == 30  && Build.VERSION.PREVIEW_SDK_INT > 0) || Build.VERSION.SDK_INT == 31
    }
}