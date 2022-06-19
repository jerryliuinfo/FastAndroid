package com.tesla.framework.kt

import androidx.databinding.ObservableInt
import java.io.InputStream
import java.io.OutputStream
import java.lang.StringBuilder
import kotlin.reflect.KProperty

/**
 * Created by Jerry on 2022/4/20.
 */


fun ObservableInt.increment(){
    set(this.get() + 1)
}

infix fun Int.times(str:String) = str.repeat(this)

infix fun String.onto(other:String) = Pair(this,other)


fun <T : Any> Iterable<T>.maxAge(lastModified: (T) -> Long): Long {
    var result = 0L
    forEach {
        result = maxOf(result, lastModified(it))
    }
    return result
}

/**
 * Faster lazy delegation for Android.
 * Warning: Only use for objects accessed on main thread
 */
fun <T> lazyAndroid(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE, initializer)
}

/**
 * 打印列表
 */
fun <T> Collection<T>.print(map:(T) -> String) =
    StringBuilder("[").also { sb ->
        this.forEach { t ->
            sb.append("\n ${map(t)},")
        }
        sb.append("]")
    }.toString()


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


/**
 * InputStream的扩展函数
 */
inline fun InputStream.copyToOut(
    output: OutputStream,
    buffSize: Int = DEFAULT_BUFFER_SIZE, progress: (l: Long) -> Unit
): Long {

    var bytesCopied = 0L
    var buff = ByteArray(buffSize)
    var readBytes = read(buff)
    while (readBytes > 0) {
        output.write(buff, 0, readBytes)
        bytesCopied += readBytes
        readBytes = read(buff)
        progress(bytesCopied);
    }

    return bytesCopied;
}