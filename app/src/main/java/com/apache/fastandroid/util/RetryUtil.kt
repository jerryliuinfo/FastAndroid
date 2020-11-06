package com.apache.fastandroid.util

import java.lang.Exception
import java.lang.RuntimeException

/**
 * author: jerry
 * created on: 2020/9/25 4:29 PM
 * description:
 */
class RetryUtil {

    var isSuccess = false
    var retryCount = 2          //尝试次数计数，此值减到0，就不会再尝试加载图片了
    fun retry(){
        while (retryCount > 0 && !isSuccess) {
            try {
                 doSomething(retryCount)
                isSuccess = true
            } catch (e:Exception) {
                System.gc()
                System.gc()
            }

            retryCount--
        }
    }

    fun doSomething(value: Int){
        if (value > 1){
            throw Exception("error")
        }

    }

}