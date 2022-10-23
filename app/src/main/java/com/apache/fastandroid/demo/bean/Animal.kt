package com.apache.fastandroid.demo.bean

/**
 * Created by Jerry on 2022/10/23.
 */
open class Animal(val name: String) {
    override fun toString(): String {
        return name
    }
}

class Dog(name: String): Animal(name)
class Cat(name: String): Animal(name)
