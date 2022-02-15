package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
class Cock(name:String = "鸡", sex:Int = Bird.MAILE,voice:String = "喔喔喔"):Chicken(name,sex, voice) {
    override fun callout(times: Int):String {
        var count = when{
            times <=0 -> 0
            times >= 10 -> 9
            else -> times
        }
        return "$name 叫了 $count 声，原来它在报晓啊"
    }
}