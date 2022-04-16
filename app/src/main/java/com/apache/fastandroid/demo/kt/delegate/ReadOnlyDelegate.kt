package com.apache.fastandroid.demo.kt.delegate

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by Jerry on 2022/4/16.
 */
class ReadOnlyDelegate:ReadOnlyProperty<Any,String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return "通过实现 ReadOnlyProperty实现, name:${property.name}"
    }
}