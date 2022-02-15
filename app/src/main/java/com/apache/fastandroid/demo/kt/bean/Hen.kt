package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
class Hen(name:String = "鸡", sex:Int = Bird.FEMAILE, voice:String = "咯咯咯"):Chicken(name,sex, voice) {
    override fun callout(times: Int):String {
        var count = when{
            times <=0 -> 0
            times >= 10 -> 9
            else -> times
        }
        return "$name 叫了 $count 声，原来它下蛋了呀"
    }
}