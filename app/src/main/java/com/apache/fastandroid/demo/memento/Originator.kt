package com.apache.fastandroid.demo.memento

/**
 * Created by Jerry on 2023/4/15.
 */
class Originator(var state:String) {

    fun createMemento():Memento{
        return Memento(state)
    }

    fun restoreMemento(memento: Memento){
        state = memento.state
    }
}