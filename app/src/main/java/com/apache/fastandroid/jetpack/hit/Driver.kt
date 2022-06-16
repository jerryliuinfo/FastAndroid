package com.apache.fastandroid.jetpack.hit

import com.blankj.utilcode.util.ToastUtils
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class Driver @Inject constructor() {

    fun drive(){
        ToastUtils.showShort("Driver driver")
    }
}