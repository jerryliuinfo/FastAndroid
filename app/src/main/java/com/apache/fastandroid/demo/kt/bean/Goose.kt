package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
class Goose(name:String,sex:Int):Bird(name,sex),Behavior {
    override fun fly(): String {
        return "Goose fly"
    }

    override fun swim(): String {
        return "Goose swim"
    }

    override var skilledSports: String = "游泳"

}