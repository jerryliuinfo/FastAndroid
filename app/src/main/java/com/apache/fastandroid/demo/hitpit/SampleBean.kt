package com.apache.fastandroid.demo.hitpit

/**
 * Created by Jerry on 2021/12/14.
 */
data class SampleBean( var id:Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SampleBean

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}