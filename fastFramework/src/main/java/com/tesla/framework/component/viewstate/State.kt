package com.tesla.framework.component.viewstate

import androidx.annotation.StringRes
import com.zwb.mvvm_mall.base.viewstate.StateType

data class State(var code: StateType, var urlKey:String="", var message:String="", @StringRes var tip:Int=0)