package com.tesla.framework.component.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

/**
 * Created by Jerry on 2022/10/18.
 * 不会发送相同值的 LiveData
 */

fun <T> LiveData<T>.distinct(): MediatorLiveData<T> = DistinctLiveData(this)


fun <X, Y> LiveData<X>.map(mapper: (X) -> Y) =
    Transformations.map(this, mapper)!!