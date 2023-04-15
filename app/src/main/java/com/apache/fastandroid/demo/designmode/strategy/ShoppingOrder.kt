package com.apache.fastandroid.demo.designmode.strategy

/**
 * Created by Jerry on 2023/4/15.
 */
class ShoppingOrder(private val amount:Double, private val payPalStrategy: IPaymentStrategy) {

    fun processOrder(){
        payPalStrategy.pay(amount)
    }

}