package com.apache.fastandroid.demo.kt.delegate

import kotlin.reflect.KProperty

/**
 * Created by Jerry on 2023/8/20.
 */
class Example {

    var message:String by Delegate()


    private class Delegate{

        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, thank you for delegating '${property.name}' to me!"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$value has been assigned to '${property.name}' in $thisRef.")
        }
    }
}