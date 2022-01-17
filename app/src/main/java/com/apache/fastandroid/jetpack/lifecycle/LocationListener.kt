package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.lifecycle.LifecycleObserverAdapter

/**
 * Created by Jerry on 2020/11/1.
 */
class LocationListener(lifecycleOwner: LifecycleOwner, private val onLocationChangeListener: OnLocationChangeListener?): DefaultLifecycleObserver {

    companion object{
        const val TAG = "JetPackLifeCycleListener"
    }
    init {
        //添加另外一个观察者
//        lifecycleOwner.lifecycle.addObserver(LifecycleObserverAdapter(lifecycleOwner,this))
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        NLog.d(TAG, "JetPackLifeCycleListener onCreate")
        initLocationMannager()
    }


    override fun onDestroy(owner: LifecycleOwner) {
        NLog.d(TAG, "JetPackLifeCycleListener onDestroy")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        startLocation()

        onLocationChangeListener?.onLocationChanged(100,100)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopLocation()
    }

    private fun initLocationMannager(){
        NLog.d(TAG, "initLocationMannager  -->")
    }

    private fun startLocation(){
        NLog.d(TAG, "startLocation  -->")
    }

    private fun stopLocation() {
        NLog.d(TAG, "stopLocation  -->")

    }


    interface OnLocationChangeListener{
        fun onLocationChanged(latitude: Int, longtitude: Int)
    }

}