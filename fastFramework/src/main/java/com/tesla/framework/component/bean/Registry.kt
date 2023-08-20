package com.tesla.framework.component.bean

import java.util.Collections
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by Jerry on 2023/8/20.
 */
open class Registry<T> {

    private var comparator: Comparator<in T>? = null

    private val registrantList: AtomicReference<List<T>> = AtomicReference<List<T>>(ArrayList())

    protected val isReverseOrder: Boolean
        protected get() = false

    protected fun onRegistrantListChange(list: List<T>?, list2: List<T>?) {
        println("onRegistrantListChange list size:${list?.size}, list2:${list2?.size}")
    }

    fun setComparator(comparator: Comparator<in T>?) {
        this.comparator = comparator
    }

    fun register(t: T): Boolean {
        var list: List<T>?
        var arrayList: ArrayList<T>
        if (t != null) {
            do {
                list = registrantList.get()
                if (list.contains(t)) {
                    return false
                }
                arrayList = ArrayList(list.size + 1)
                val isReverseOrder = isReverseOrder
                if (isReverseOrder) {
                    arrayList.add(t)
                }
                if (list.isNotEmpty()) {
                    arrayList.addAll(list)
                }
                if (!isReverseOrder) {
                    arrayList.add(t)
                }
            } while (!setUnsortedList(list, arrayList))
            return true
        }
        return false
    }

    fun unregister(t: T): Boolean {
        var list: List<T>
        var arrayList: ArrayList<T>? = null
        if (t != null) {
            do {
                list = registrantList.get()
                val size = list.size
                if (size > 0) {
                    arrayList = ArrayList(size)
                    for (t2 in list) {
                        if (t != t2) {
                            arrayList.add(t2)
                        }
                    }
                }
            } while (!setSortedList(list, arrayList))
            return true
        }
        return false
    }

    fun unregisterAll(): Boolean {
        var list: List<T>?
        do {
            list = registrantList.get()
            if (list?.isEmpty() == true) {
                return false
            }
        } while (!setSortedList(list, ArrayList(0)))
        return true
    }

    fun setList(list: List<T>?): Boolean {
        if (list == null) {
            return false
        }
        var list2: List<T>?
        do {
            list2 = registrantList.get()
            if (list2 == list) {
                return false
            }
        } while (!setUnsortedList(list2, list))
        return true
    }

    private fun setSortedList(list: List<T>?, list2: List<T>?): Boolean {
        if (registrantList.compareAndSet(list, Collections.unmodifiableList(list2))) {
            onRegistrantListChange(list, list2)
            return true
        }
        return false
    }

    private fun setUnsortedList(list: List<T>?, list2: List<T>?): Boolean {
        val comparator: Comparator<in T>? = comparator
        if (comparator != null) {
            Collections.sort(list2, comparator)
        }
        return setSortedList(list, list2)
    }

    val list: List<T>?
        get() = registrantList.get()
}