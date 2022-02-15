package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
interface Behavior {

    fun fly():String

    fun swim():String

    fun run():String{
        return "大多数鸟儿跑的并不像样，只有鸵鸟等少数鸟类才擅长奔跑"
    }

    //抽象属性
    var skilledSports:String


}