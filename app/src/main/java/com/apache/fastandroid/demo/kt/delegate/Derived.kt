package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2023/8/20.
 * 使用 by b 后， Derived 就不必显式地实现 Base 中定义的方法了
 */
class Derived(b:Base):Base by b {
    // 在 b 的 `print` 实现中不会访问到这个属性
    override val message: String = "Message of Derived"

    override fun printLine() {
        println("abc")
    }
}