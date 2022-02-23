package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2022/2/22.
 */
class DelegateList<T>: ArrayList<T>() {

    var deletedItem:T ?= null

    override fun remove(element: T): Boolean {
        deletedItem = element
        return super.remove(element)
    }

    fun recover():T?{
        return deletedItem
    }
}