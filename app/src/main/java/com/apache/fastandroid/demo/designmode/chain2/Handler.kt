package com.apache.fastandroid.demo.designmode.chain2

// Handler 接口
interface Handler {
    fun handle(request: Request?): Response?
}