package com.apache.fastandroid.demo.kt.refied

import java.lang.IllegalStateException

/**
 * Created by Jerry on 2022/2/22.
 */
class RefiedDemo {


    fun <T> printType(){
        //报错 Cannot use 'T' as reified type parameter. Use a class instead.
//        println(T::class.java)
    }

    fun <T> printType2(clazz: Class<T>){
        println(clazz::class.java)
    }


    /**
     *  当调用 refied 修饰的内容时，编译器会复制该函数体，并将范型替换为实际使用的类型，
     *  这样就可以不用将类传递给函数也能获取相应的类型信息了，反编译结果
     */
    inline fun <reified T> printType3(){
        println(T::class.java)
    }

    fun printStringType(){
        printType3<String>()
    }

    fun printIntType(){
        printType3<Int>()
    }


    //重载函数返回参数类型
    inline fun <reified T> calculate(value: Float): T {
        return when (T::class) {
            Float::class -> value as T
            Int::class -> value.toInt() as T
            else -> throw IllegalStateException("Only works with Float and Int")
        }
    }


}