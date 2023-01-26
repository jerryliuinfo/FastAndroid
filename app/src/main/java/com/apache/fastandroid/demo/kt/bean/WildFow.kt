package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 * 没有 by 关键字时，必须实现 Behavior 接口的所有方法
 */
class WildFow(name:String, sex:Int = Bird.MAILE,val behavior: Behavior):Bird(name,sex),Behavior{
    override fun fly(): String {
        return behavior.fly()
    }

    override fun swim(): String {
        return behavior.swim()

    }

    override var skilledSports: String
        get() = behavior.skilledSports
            set(value) {
                behavior.skilledSports = value
            }
}


/**
 * 有了 by 关键字就没必要实现 Behavior 接口中的所有方法，可以选择性地挑选其中一些方法来实现
 */
class WildFowBy(name:String, sex:Int = Bird.MAILE,behavior: Behavior):Bird(name,sex),Behavior by behavior {
}