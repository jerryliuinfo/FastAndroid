package com.tesla.framework.component.livedata

import androidx.lifecycle.ViewModel

/**
 * Created by Jerry on 2020/12/31.
 */
class SingleTonViewModel: ViewModel() {

    val singleTonLiveData by lazy {
        SingleTonLiveData.getInstance()
    }

    fun changeContext(text:String){
        singleTonLiveData.value = text
    }
}