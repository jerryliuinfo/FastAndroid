package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
class BehaviorFly:Behavior {
    override fun fly(): String {
        return "翱翔天空"
    }

    override fun swim(): String {
        return "落水凤凰不如鸡"
    }

    override fun run(): String {
        return "能飞干嘛还要走"
    }

    override var skilledSports: String = "飞翔"

}