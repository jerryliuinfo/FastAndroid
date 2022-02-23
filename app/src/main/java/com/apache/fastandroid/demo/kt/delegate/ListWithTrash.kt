package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2022/2/22.
 * by 关键字告诉 Kotlin 将 MutableList 接口的功能委托给一个名为 innerList 的内部 ArrayList。
 * 通过桥接到内部 ArrayList 对象方法的方式，ListWithTrash 仍然支持 MutableList 接口中的所有函数。与此同时，现在您可以添加自己的行为了
 * 在您无法继承特定类型时，委托模式就显得十分有用。通过使用类代理，您的类可以不继承于任何类。
 * 相反，它会与其内部的源类型对象共享相同的接口，并对该对象进行装饰。这意味着您可以轻松切换实现而不会破坏公共 API
 */
class ListWithTrash<T>(private val innerList:MutableList<T> = ArrayList<T>()):MutableCollection<T> by innerList {
    var deletedItem:T ?= null
    override fun remove(element: T): Boolean {

        deletedItem = element
        return innerList.remove(element)
    }

    fun recover():T?{
        return deletedItem
    }
}