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
        return formatString
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        if (thisRef is People){
            thisRef.updateCount++
        }

        formatString = value.lowercase(Locale.getDefault()).capitalize()
    }
}