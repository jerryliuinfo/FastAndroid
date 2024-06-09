package com.apache.fastandroid.jetpack.lifecycle.service

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2024/6/9.
 * 感知生命周期变化
 */
class MyServiceObserver : DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        // Service 启动时的操作
        Logger.d("MyServiceObserver onStart")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        // Service 销毁时的操作
        Logger.d("MyServiceObserver onDestroy")

    }
}