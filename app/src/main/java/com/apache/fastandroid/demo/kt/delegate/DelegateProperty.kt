package com.apache.fastandroid.demo.kt.delegate

import kotlin.reflect.KProperty

/**
 * Created by Jerry on 2022/5/1.
 */
class DelegateProperty {

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {        // 2
        return "$thisRef, thank you for delegating '${prop.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) { // 2
        println("$value has been assigned to ${prop.name} in $thisRef")
    }
}