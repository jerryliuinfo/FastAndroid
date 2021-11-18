package com.tesla.framework.component.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Jerry on 2020/12/31.
 */
class SingleTonViewModel: ViewModel() {
    private val liveData = MutableLiveData<String>()

    val singleTonLiveData by lazy {
        liveData
    }

    fun changeContext(text:String){
        singleTonLiveData.value = text
    }
}