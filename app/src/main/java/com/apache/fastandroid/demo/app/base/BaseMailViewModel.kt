package com.apache.fastandroid.demo.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apache.fastandroid.R
import com.tesla.framework.common.util.CommonUtil
import com.zwb.mvvm_mall.base.viewstate.State

/**
 * Created by Jerry on 2022/4/18.
 */
open class BaseMailViewModel<R: BaseMailRepository>:ViewModel() {
     val loadState by lazy {
         MutableLiveData<State>()
     }

    val mRepository by lazy {
        CommonUtil.getClass<R>(this)
            .getDeclaredConstructor(MutableLiveData::class.java)
            .newInstance(loadState)
    }

    override fun onCleared() {
        super.onCleared()

    }
}