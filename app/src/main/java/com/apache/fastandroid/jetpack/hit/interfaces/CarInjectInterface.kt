package com.apache.fastandroid.jetpack.hit.interfaces

import com.apache.fastandroid.jetpack.hit.tyre.Tyre
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 * 注入接口
 */
class CarInjectInterface @Inject constructor() {

    @Inject
    lateinit var tyre: Tyre

    fun deliveryByEngine(){
        tyre.start()
    }

}