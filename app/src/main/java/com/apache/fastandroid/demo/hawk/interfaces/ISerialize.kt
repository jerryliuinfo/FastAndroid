package com.apache.fastandroid.demo.hawk.interfaces

import com.apache.fastandroid.demo.hawk.bean.DataInfo

/**
 * Created by Jerry on 2022/1/20.
 */
interface ISerialize {

    fun <T> serialize(cliperText:String, value:T):String

    fun deserialize(plainText:String):DataInfo
}