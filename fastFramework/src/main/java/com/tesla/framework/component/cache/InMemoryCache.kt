package com.tesla.framework.component.cache

/**
 * Created by Jerry on 2023/5/2.
 */
internal class InMemoryCache<K:Any, V:Any>(private val cache:MutableMap<K,V> = mutableMapOf()):Cache<K,V> {
    override fun get(k: K): V? {
        return cache[k]
    }

    override fun set(k: K, v: V) {
        cache[k] = v
    }

    override fun hasKey(k: K): Boolean {
        return cache.containsKey(k)
    }

    override fun clear() {
        cache.clear()
    }
}