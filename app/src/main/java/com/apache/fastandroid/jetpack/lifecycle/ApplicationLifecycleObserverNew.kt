package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.lifecycle.LifecycleObserverAdapter
import com.tesla.framework.component.lifecycle.SimpleLifeCycleObserver

/**
 * Created by Jerry on 2021/2/8.
 */
class ApplicationLifecycleObserverNew(lifecycleOwner: LifecycleOwner): SimpleLifeCycleObserver() {
    companion object{
        private const val TAG = "ApplicationLifecycleObserver"
    }

    init {
        lifecycleOwner.lifecycle.addObserver(LifecycleObserverAdapter(lifecycleOwner,this))
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        NLog.d(TAG, "onAppForground --->")
        ToastUtils.showShort("App已进入前台...")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)

        NLog.d(TAG, "onAppBackground --->")
        ToastUtils.showShort("App已进入后台....")
    }


    /**
     * 永远不会被调用，系统不会纷发 ON_DESTROY 事件
     */
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        NLog.d(TAG, "onDestroy --->")

    }

}