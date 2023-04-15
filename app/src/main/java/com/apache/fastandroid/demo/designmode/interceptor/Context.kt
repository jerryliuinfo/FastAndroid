package com.apache.fastandroid.demo.designmode.interceptor

/**
 * Created by Jerry on 2023/4/12.
 */
class Context(var input: String, var output: Int)

interface Expression {

    fun interpret(context: Context)

}