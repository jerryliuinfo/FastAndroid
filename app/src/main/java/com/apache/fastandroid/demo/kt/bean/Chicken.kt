package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
abstract class Chicken(name: String, sex:Int,var voice:String):Bird(name,sex) {

    abstract fun callout(times:Int):String
}