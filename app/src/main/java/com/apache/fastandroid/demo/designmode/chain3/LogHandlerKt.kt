package com.apache.fastandroid.demo.designmode.chain3

import com.apache.fastandroid.demo.designmode.chain2.Handler
import com.apache.fastandroid.demo.designmode.chain2.Request
import com.apache.fastandroid.demo.designmode.chain2.Response

/**
 * Created by Jerry on 2023/2/22.
 */
class LogHandlerKt:Handler {
    override fun handle(request: Request): Response? {
        println("Making request to ${request.url}")
        return null
    }
}