package com.apache.fastandroid.jetpack.lifecycle.location

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.component.logger.Logger
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Created by Jerry on 2024/6/9.
 */
class LocationManager(private val lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {

    companion object{
        const val TAG = "Location"
    }

    var isActive = false

    private var mDisposable: Disposable? = null

    private val mObservers = mutableListOf<ILocationListener>()

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }


    fun addListener(listener: ILocationListener) {
        if (!mObservers.contains(listener)) {
            mObservers.add(listener)
        }
    }

    fun removeListener(listener: ILocationListener) {
        if (mObservers.contains(listener)) {
            mObservers.remove(listener)
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        Logger.d("$TAG onResume")
        setActive(owner.lifecycle)
        calculte()
    }

    override fun onPause(owner: LifecycleOwner) {
        Logger.d("$TAG onPause")
        setActive(owner.lifecycle)
    }


    private fun setActive(lifecycle: Lifecycle) {
        isActive = lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
        Logger.d("$TAG setActive isActive:%s", isActive)
        mDisposable?.dispose()
    }

    private fun calculte() {
        Logger.d("$TAG begin calculate")
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .subscribe {
                val location = Random.nextLong(100)
                Logger.d("$TAG onLocation change:$location, isActive:$isActive")
                if (!isActive){
                    return@subscribe
                }

                mObservers.forEach {
                    it.onLocationChanged(location)
                }
            }

    }

}