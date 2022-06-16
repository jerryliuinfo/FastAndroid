package com.apache.fastandroid.jetpack.hit.tyre

import com.blankj.utilcode.util.ToastUtils
import javax.inject.Inject

/**
 * Created by Jerry on 2022/6/16.
 */
class ChinaTyre @Inject constructor() :Tyre{
    override fun start() {
        ToastUtils.showShort("ChinaTyre start")
    }
}