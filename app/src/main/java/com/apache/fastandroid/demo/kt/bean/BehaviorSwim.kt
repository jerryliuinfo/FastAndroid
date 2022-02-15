package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
class BehaviorSwim:Behavior {
    override fun fly(): String {
        return "看情况，大雁能展翅高飞，企鹅却欲飞还林"
    }

    override fun swim(): String {
        return "依然戏水"
    }

    override fun run(): String {
        return "赶鸭子上架"
    }

    override var skilledSports: String = "游泳"

}