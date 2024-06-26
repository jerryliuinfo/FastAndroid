package com.tesla.framework.kt

import java.lang.StringBuilder
import kotlin.reflect.KProperty

/**
 * Created by Jerry on 2022/12/4.
 */


/**
 * 打印列表
 */
fun <T> Collection<T>.print(mapper: (T) -> String) =
    StringBuilder("\n[").also { sb ->
        //遍历集合元素将元素转换成感兴趣的字串，并独占一行
        this.forEach { e -> sb.append("\n\t${mapper(e)},") }
        sb.append("\n]")
    }.toString()

fun <T> Collection<T>.print2(mapper: (T) -> String):String {
    return StringBuilder("\n[").also { sb ->
        //遍历集合元素将元素转换成感兴趣的字串，并独占一行
        this.forEach { e -> sb.append("\n\t${mapper(e)},") }
        sb.append("\n]")
    }.toString()
}



/**
 * 打印 Map，生成结构化键值对子串
 * @param space 行缩进量
 */
fun <K, V> Map<K, V?>.print(space: Int = 0): String {
    //'生成当前层次的行缩进，用space个空格表示，当前层次每一行内容都需要带上缩进'
    val indent = StringBuilder().apply {
        repeat(space) { append(" ") }
    }.toString()
    return StringBuilder("\n${indent}{").also { sb ->
        this.iterator().forEach { entry ->
            //'如果值是 Map 类型，则递归调用print()生成其结构化键值对子串，否则返回值本身'
            val value = entry.value.let { v ->
                (v as? Map<*, *>)?.print("${indent}${entry.key} = ".length) ?: v.toString()
            }
            sb.append("\n\t${indent}[${entry.key}] = $value,")
        }
        sb.append("\n${indent}}")
    }.toString()
}

/**
 * 为任意 Kotlin 中的类添加一个扩展函数，它的功能是将data class中所有的字段名及其对应值存在一个 map 中
 * https://mp.weixin.qq.com/s/grqcsJRY_3J9HyuKJRwQdw
 *
 */
fun Any.ofMap(): Map<String, Any?>? {
    return this::class.takeIf { it.isData }
        ?.members?.filterIsInstance<KProperty<Any>>()
        ?.map { member ->
            val value = member.call(this)?.let { v->
                //'若成员变量是data class，则递归调用ofMap()，将其转化成键值对，否则直接返回值'
                if (v::class.isData) v.ofMap()
                else v
            }
            member.name to value
        }
        ?.toMap()
}