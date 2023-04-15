package com.apache.fastandroid.demo.designmode.composite

/**
 * Created by Jerry on 2023/3/25.
 * 定义组合接口
 */
interface Component {
    fun add(component: Component)

    fun remove(component: Component)

    fun get(index: Int): Component

    fun operation()
}