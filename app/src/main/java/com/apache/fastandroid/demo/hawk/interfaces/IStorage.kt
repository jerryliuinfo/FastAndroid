package com.apache.fastandroid.demo.hawk.interfaces

/**
 * Created by Jerry on 2022/1/19.
 */
interface IStorage {

    fun <T> put(key:String, value:T):Boolean


    fun <T> get(key:String):T?

    fun delete(key:String):Boolean

    fun deleteAll():Boolean

    fun count():Int

    fun contains(key: String):Boolean


}