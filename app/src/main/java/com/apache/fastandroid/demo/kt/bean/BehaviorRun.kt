package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
class BehaviorRun:Behavior {
    override fun fly(): String {
        return "飞不起来"
    }

    override fun swim(): String {
        return "望洋兴叹"
    }



    override var skilledSports: String = "奔跑"

}