package com.apache.fastandroid.demo.designmode.composite

/**
 * Created by Jerry on 2023/3/25.
 */
class Leaf(private val name:String):Component {
    override fun add(component: Component) {
        throw UnsupportedOperationException()
    }

    override fun remove(component: Component) {
        throw UnsupportedOperationException()
    }

    override fun get(index: Int): Component {
        throw UnsupportedOperationException()
    }

    override fun operation() {
        println("叶子对象 $name 的操作")
    }
}