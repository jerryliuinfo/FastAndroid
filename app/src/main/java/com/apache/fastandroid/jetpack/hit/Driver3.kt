package com.apache.fastandroid.jetpack.hit

import android.app.Activity
import android.app.Application
import com.blankj.utilcode.util.ToastUtils
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 * 如果你的某个类依赖于Application或者Activity，不需要想办法为这两个类提供依赖注入的实例，Hilt自动就能识别它们。
 */
class Driver3 @Inject constructor(val activity: Activity) {

    fun drive(){
        println("Driver2 drive: ${activity}")
    }
}