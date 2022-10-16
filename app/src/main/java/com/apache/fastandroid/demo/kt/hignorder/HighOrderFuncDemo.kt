package com.apache.fastandroid.demo.kt.hignorder

/**
 * Created by Jerry on 2022/10/16.
 */
class HighOrderFuncDemo {
}


fun highOrderA(function: (Int) -> String):String{
    return function(1)
}

fun highOrderB(param:Int):String{
    return param.toString()
}

inline fun myWith(name: String, block: String.() -> Unit){
    name.block()
}

/**
 * 匿名函数:并非每个函数都需要一个名称。某些函数通过输入和输出更直接地进行标识。这些函数称为“匿名函数”。您可以保留对某个匿名函数的引用，以便日后使用此引用来调用该匿名函数。
 */
val stringLengthFunc:(String) -> Int = { input ->
    input.length
}

/**
 * 一个函数可以将另一个函数当作参数。将其他函数用作参数的函数称为“高阶函数”。此模式对组件之间的通信（其方式与在 Java 中使用回调接口相同）很有用。
 */
fun stringMapperFunc(str:String, mapper:(String) -> Int):Int{
    return mapper(str)
}