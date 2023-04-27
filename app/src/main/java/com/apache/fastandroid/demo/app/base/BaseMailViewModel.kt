package com.apache.fastandroid.demo.app.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.home.HomeReporsitoryKt
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.network.model.DataHolder
import com.apache.fastandroid.network.response.BaseResponse
import com.tesla.framework.common.util.CommonUtil
import com.tesla.framework.component.livedata.SingleLiveEvent
import com.tesla.framework.component.viewstate.State
import kotlinx.coroutines.launch

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