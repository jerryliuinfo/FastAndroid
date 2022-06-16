package com.apache.fastandroid.jetpack.hit

import com.blankj.utilcode.util.ToastUtils
import javax.inject.Inject

class Repository @Inject constructor() {

    fun doRepositoryWork() {
        println("Do some work in Repository.")
        ToastUtils.showShort("Do some work in Repository")
    }

}