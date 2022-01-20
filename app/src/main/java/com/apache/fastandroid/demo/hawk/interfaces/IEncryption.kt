package com.apache.fastandroid.demo.hawk.interfaces

/**
 * Created by Jerry on 2022/1/19.
 */
interface IEncryption {

    fun doInit():Boolean

    fun encrypt(key:String, value:String):String?

    fun decrypt(key: String, value: String):String?
}