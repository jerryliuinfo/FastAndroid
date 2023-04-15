package com.apache.fastandroid.demo.designmode.strategy

/**
 * Created by Jerry on 2023/4/15.
 */
class PayPalStrategy(private val email:String, private val password:String):IPaymentStrategy {
    override fun pay(amount: Double) {
        println("$amount paid using PayPal [Email: $email, Password: $password]")
    }
}