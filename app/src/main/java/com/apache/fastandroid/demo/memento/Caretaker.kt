package com.apache.fastandroid.demo.memento

/**
 * Created by Jerry on 2023/4/15.
 */
class Caretaker {

    private val mementos = mutableListOf<Memento>()

    fun addMemento(memento: Memento){
        mementos.add(memento)
    }

    fun getMemento(index:Int):Memento{
        return mementos[index]
    }

}