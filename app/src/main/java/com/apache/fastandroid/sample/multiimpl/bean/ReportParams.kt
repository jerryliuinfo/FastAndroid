package com.apache.fastandroid.sample.multiimpl.bean

import java.io.Serializable

/**
 * Created by Jerry on 2022/1/15.
 */
open class ReportParams:Iterable<Any?>, Serializable {
    val data = HashMap<String,String?>()
        get() {
            return field
        }
    override fun iterator(): Iterator<Any?> {
        return data.iterator()
    }

    operator fun set(key:String, value:Any?):ReportParams{
        data[key] = value?.toString()
        return this
    }

    operator fun get(key:String):String? = data[key]

    fun get(key:String, default:String?) = data[key] ?: default

    fun setIfNull(key:String, value:Any?):ReportParams{
        val oldValue = data[key]
        if (oldValue == null){
            data[key] = value?.toString()
        }
        return this
    }


    fun merge(other: ReportParams?): ReportParams {
        if (null != other) {
            for ((key, value) in other.data) {
                setIfNull(key, value)
            }
        }
        return this
    }

    override fun toString(): String {
        return StringBuilder().apply {
            append("[")
            for ((key, value) in data) {
                append(" $key = $value ,")
            }
            deleteCharAt(this.length - 1)
            append("]")
        }.toString()
    }
}