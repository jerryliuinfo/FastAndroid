package com.apache.fastandroid.jetpack.livedataviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tesla.framework.component.livedata.LiveDataBus

/**
 * Created by Jerry on 2020/11/5.
 */
class NameViewModel:ViewModel() {

    val nameLiveData = LiveDataBus.StickyLiveData<String>("")



}