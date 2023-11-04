package com.tesla.framework.common.interfaces

/**
 * Created by Jerry on 2023/11/2.
 */
interface IConsumer<T> {
    fun accept(t: T)
}