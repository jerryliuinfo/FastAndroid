package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.component.logger.Logger

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
        Logger.d("JetPackLifeCycleListener onCreate")
        initLocationMannager()
    }


    override fun onDestroy(owner: LifecycleOwner) {
        Logger.d( "JetPackLifeCycleListener onDestroy")
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
        Logger.d( "initLocationMannager  -->")
    }

    private fun startLocation(){
        Logger.d( "startLocation  -->")
    }

    private fun stopLocation() {
        Logger.d( "stopLocation  -->")

    }


    interface OnLocationChangeListener{
        fun onLocationChanged(latitude: Int, longtitude: Int)
    }

}