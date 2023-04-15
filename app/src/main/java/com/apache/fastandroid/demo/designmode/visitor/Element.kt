package com.apache.fastandroid.demo.designmode.visitor

/**
 * Created by Jerry on 2023/4/15.
 */
interface Element {

    fun accept(visitor: Visitor)
}