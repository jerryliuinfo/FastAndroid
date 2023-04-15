package com.apache.fastandroid.demo.designmode.strategy

/**
 * Created by Jerry on 2023/4/15.
 */
class CreditCardStrategy(private val name:String, private val cardNum:String, private val cvv:String):IPaymentStrategy {
    override fun pay(amount: Double) {
        println("$amount paid with credit card [Name: $name, Card Number: $cardNum, CVV: $cvv]")
    }
}