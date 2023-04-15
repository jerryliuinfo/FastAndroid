package com.apache.fastandroid.demo.designmode.interceptor

/**
 * Created by Jerry on 2023/4/12.
 */
class AddExpression(var operand1:Expression,var operand2:Expression ):Expression {
    override fun interpret(context: Context) {
        operand1.interpret(context)
        operand2.interpret(context)
        context.output = context.output + 1
    }
}