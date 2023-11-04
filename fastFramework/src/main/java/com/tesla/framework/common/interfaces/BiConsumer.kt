package com.tesla.framework.common.interfaces

/**
 * Created by Jerry on 2023/11/2.
 */
interface BiConsumer<T, U> {
    fun accept(t: T, u: U)
}