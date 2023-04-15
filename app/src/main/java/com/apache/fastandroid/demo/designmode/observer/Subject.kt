package com.apache.fastandroid.demo.designmode.observer

/**
 * Created by Jerry on 2023/3/12.
 */
interface Subject {

    fun attach(observer: Observer)

    fun detach(observer: Observer)

    fun notifyObservers()
}