package com.apache.fastandroid.demo.kt.delegate

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Jerry on 2022/4/16.
 */
class ReadWriteDelegate:ReadWriteProperty<Any,Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return 20
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        println("委托属性为: ${property.name}, 委托值为: ${value}")
     }

}