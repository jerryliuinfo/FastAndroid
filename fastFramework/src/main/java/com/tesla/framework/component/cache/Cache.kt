package com.tesla.framework.component.cache

/**
 * Created by Jerry on 2023/5/2.
 */
interface Cache<K : Any, V : Any> {

    operator fun get(k: K): V?

    operator fun set(k: K, v: V)

    fun hasKey(k: K): Boolean

    fun clear()
}