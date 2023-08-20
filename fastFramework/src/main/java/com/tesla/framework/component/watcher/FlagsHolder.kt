package com.tesla.framework.component.watcher

import com.tesla.framework.common.util.BitUtil


/**
 * Created by Jerry on 2023/8/19.
 */
class FlagsHolder(val indexBase:Boolean): IntHolderImpl() {

    fun addFlag(value:Int):Boolean{
        val newValue = BitUtil.addFlag(getValue(),value, indexBase)
        return setValue(newValue) != newValue
    }

    fun removeFlag(value:Int):Boolean{
        val newValue =  BitUtil.removeFlag(getValue(),value, indexBase)
        return setValue(newValue) != newValue
    }

    fun hasFlag(value: Int):Boolean{
        return BitUtil.hasFlag(getValue(),value,indexBase)
    }

    fun syncFlag(flag:Int, add:Boolean):Boolean{
        return if (add) addFlag(flag) else removeFlag(flag)
    }
}