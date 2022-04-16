package com.apache.fastandroid.demo.kt.delegate

import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Jerry on 2022/2/22.
 */
class FormatDelegate:ReadWriteProperty<Any?, String> {
    private var formatString:String = ""
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("$thisRef, thank you for delegating '${property.name}' to me!")
        return formatString
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")

        if (thisRef is People){
            thisRef.updateCount++
        }

        formatString = value.lowercase(Locale.getDefault()).capitalize()
    }
}