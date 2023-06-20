package com.apache.fastandroid.demo.designmode.chain2

/**
 * Created by Jerry on 2023/2/22.
 */
// 示例 Handler
class LoggingHandler : Handler {
    override fun handle(request: Request?): Response? {
        println("Making request to " + request!!.url)
        return null
    }
}