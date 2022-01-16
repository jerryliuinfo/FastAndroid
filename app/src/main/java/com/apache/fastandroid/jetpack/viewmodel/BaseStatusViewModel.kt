package com.apache.fastandroid.jetpack.viewmodel

import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.jetpack.StateData
import com.apache.fastandroid.jetpack.StateLiveData

/**
 * Created by Jerry on 2022/1/16.
 */
open class BaseStatusViewModel :BaseViewModel() {

    val stateLiveData = StateLiveData<StateData<*>>()




}