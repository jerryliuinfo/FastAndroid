package com.apache.fastandroid.demo.blacktech.permissionmonitor

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/1/15.
 */
class Host {

    private val TAG = "Host"

    fun fuck(){
       Logger.d(TAG, "fuck:")
        test()
    }

    fun fuck(i:Int){
        Logger.d(TAG, "fuck called with i:${i}")

    }

    fun test(){

    }
}