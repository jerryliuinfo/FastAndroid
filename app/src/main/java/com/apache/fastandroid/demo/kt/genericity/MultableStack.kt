package com.apache.fastandroid.demo.kt.genericity

/**
 * Created by Jerry on 2022/4/25.
 * 泛型类
 */
class MultableStack<E>(vararg items:E) {
    private val elements = items.toMutableList()

    fun push(element:E) = elements.add(element)

    fun peek():E = elements.last()

    fun pop():E = elements.removeAt(elements.lastIndex)

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    override fun toString(): String {
        return "MultableStack(${elements.joinToString("/")}})"
    }
}


/**
 * 泛型函数
 * 如果函数的逻辑与特定类型无关，那么还可以泛化函数。例如，你可以编写一个工具函数来创建可变堆栈:
 */
fun <E> mutableStackOf(vararg elements:E) = MultableStack(*elements)