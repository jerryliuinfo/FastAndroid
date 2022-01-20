package com.apache.fastandroid.demo.hawk.interfaces

import java.lang.reflect.Type

/**
 * Created by Jerry on 2022/1/20.
 */
interface IParser {

    fun <T>  fromJson(value:String, type:Type):T

    fun toJson(body: Any?): String?
}