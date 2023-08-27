package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2023/8/20.
 */
class BaseImpl(val x: Int) : Base {
    override val message: String
        get() = "BaseImpl: x = $x"

    override fun print() {
        println("BaseImpl print $x")
    }

    override fun printLine() {
        println("BaseImpl printLine $x")
    }
}