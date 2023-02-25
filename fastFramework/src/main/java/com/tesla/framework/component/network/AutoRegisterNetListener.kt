package com.tesla.framework.component.network

import androidx.lifecycle.*
import com.zwb.lib_base.utils.network.NetworkStateChangeListener
import com.zwb.lib_base.utils.network.NetworkStateClient

/**
 * 自动注册网络状态监听
 * 使用的是[androidx.lifecycle.LifecycleObserver]来同步生命周期
 *
 * @author Qu Yunshuo
 * @since 2021/7/11 4:56 下午
 */
class AutoRegisterNetListener constructor(listener: NetworkStateChangeListener) :
    DefaultLifecycleObserver {

    /**
     * 当前需要自动注册的监听器
     */
    private var mListener: NetworkStateChangeListener? = null

    init {
        mListener = listener
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        mListener?.run { NetworkStateClient.setListener(this) }
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        NetworkStateClient.removeListener()
        mListener = null
    }


}