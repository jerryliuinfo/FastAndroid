package com.tesla.framework.support.bean

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

/**
 * Created by Jerry on 2023/9/29.
 */
data class TabItemKt(var type:String, @DrawableRes val text:Int, @DrawableRes val icon:Int ?= null, val fragment:Fragment) {
}