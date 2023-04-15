package com.apache.fastandroid.demo.designmode.composite

/**
 * Created by Jerry on 2023/3/25.
 * 实现组合接口
 */
class Composite(private val name:String):Component {

    private val children = mutableListOf<Component>()

    override fun add(component: Component) {
        children.add(component)
    }

    override fun remove(component: Component) {
        children.remove(component)
    }

    override fun get(index: Int): Component {
        return children[index]
    }

    override fun operation() {
        println("组合对象 ${name} 的操作")
        for (child in children){
            child.operation()
        }
    }
}