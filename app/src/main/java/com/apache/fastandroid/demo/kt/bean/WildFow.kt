package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
class WildFow(name:String, sex:Int = Bird.MAILE,behavior: Behavior):Bird(name,sex),Behavior by behavior {
}