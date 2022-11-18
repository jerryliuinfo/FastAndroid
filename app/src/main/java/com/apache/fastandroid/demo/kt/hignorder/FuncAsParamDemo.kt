package com.apache.fastandroid.demo.kt.hignorder

/**
 * Created by Jerry on 2022/11/7.
 */
class FuncAsParamDemo {


    /**
     * 函数作为参数
     */
    fun sum(a: Int, b: Int, term: (Int) -> Int): Int {
        var sum = 0
        for (i in a..b) {
            sum += term(i)
        }
        return sum
    }

    val identity = { x: Int -> x }
    val square = { x: Int -> x * x }
    val cube = { x: Int -> x * x * x }

    /**
     * 函数作为返回值
     */
    fun sum(type: String): (Int, Int) -> Int {

         return when (type) {
            IDENTITY -> return { a, b, ->
                sum(a, b, identity)
            }
            SQUARE -> return { a, b -> sum(a, b, square) }
            CUBE -> return { a, b -> sum(a, b, cube) }
             else -> return { a, b, ->
                 sum(a, b, identity)
             }
        }
    }


    companion object {
        const val IDENTITY = "identity"
        const val SQUARE = "square"
        const val CUBE = "cube"
    }

}