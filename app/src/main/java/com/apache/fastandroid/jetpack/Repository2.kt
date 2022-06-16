package com.apache.fastandroid.jetpack

import com.apache.fastandroid.jetpack.hit.Driver
import com.blankj.utilcode.util.ToastUtils
import javax.inject.Inject

class Repository2 @Inject constructor(driver: Driver) {

    fun doRepositoryWork() {
        println("Do some work in Repository.")
        ToastUtils.showShort("Do some work in Repository2")
    }

}