package com.apache.fastandroid.demo.designmode.interceptor

/**
 * Created by Jerry on 2023/4/12.
 */
class NumberExpression(var number:Int):Expression {
    override fun interpret(context: Context) {
        context.output = number
    }
}