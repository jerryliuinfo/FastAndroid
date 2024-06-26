package com.apache.fastandroid.demo.performance.startup


import android.content.Context
import com.tesla.framework.component.startup.Startup

/**
 * @author : zhaoyanjun
 * @time : 2021/9/27
 * @desc :
 */
class SDK1 : Startup() {

    override fun create(context: Context) {
        //模拟初始化时间
        Thread.sleep(100)
    }

    //可以在子线程初始化
    override fun callCreateOnMainThread(): Boolean = false

    //依赖
    override fun dependencies(): List<Class<out Startup>>? {
        return null
    }

    //是否需要等待主线程
    override fun waitOnMainThread(): Boolean {
        return false
    }
}