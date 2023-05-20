package com.apache.fastandroid.demo.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tesla.framework.common.util.CommonUtil
import com.tesla.framework.component.viewstate.State

/**
 * Created by Jerry on 2022/4/18.
 */
open class BaseMailViewModel<R : BaseMailRepository> : ViewModel() {
    val loadState by lazy {
        MutableLiveData<State>()
    }




    val mRepository by lazy {
        CommonUtil.getClass<R>(this)
            .getDeclaredConstructor(MutableLiveData::class.java)
            .newInstance(loadState)
    }



}