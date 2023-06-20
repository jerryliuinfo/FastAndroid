package com.apache.fastandroid.demo.designmode.observer

/**
 * Created by Jerry on 2023/3/12.
 */
class ConcreteSubject:Subject {
    private val mObservers = mutableListOf<Observer>()
    override fun attach(observer: Observer) {
        mObservers.add(observer)
    }

    override fun detach(observer: Observer) {
        mObservers.remove(observer)

    }

    override fun notifyObservers() {
        for (observer in mObservers) {
            observer.onChanged(this)
        }
    }

    fun doSomeLogic(){
        notifyObservers()
    }
}