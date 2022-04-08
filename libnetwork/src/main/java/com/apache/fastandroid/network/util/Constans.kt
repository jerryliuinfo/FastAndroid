package com.apache.fastandroid.network.util

import androidx.annotation.IntDef

/**
 * Created by Jerry on 2022/4/8.
 */
@IntDef(ErrorCode.OK, ErrorCode.UNAUTHORIZED, ErrorCode.CUSTOM_FIRST, ErrorCode.VALUE_IS_NULL)
@Retention(AnnotationRetention.SOURCE)
annotation class ErrorCode {
    companion object {
        const val OK = 0
        const val UNAUTHORIZED = 401
        const val CUSTOM_FIRST = 600
        const val VALUE_IS_NULL = CUSTOM_FIRST + 1
    }
}