package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/1/23.
 */
open class Animal {
}

class Dog:Animal()

fun Animal.name() = "animal"
fun Dog.name() = "dog"

fun Animal.printName(animal: Animal){
    println(animal.name())
}

fun main() {
    /**
     * 输出的 是 animal，而不是 dog，因为kotlin 的扩展方法是静态地给一个类添加方法，
     * 是不具备动态运行时的多态效应,扩展函数会被编译成一个静态函数
     */
    Dog().printName(Dog())
}

val echo = { name:String ->
    println(name)
}