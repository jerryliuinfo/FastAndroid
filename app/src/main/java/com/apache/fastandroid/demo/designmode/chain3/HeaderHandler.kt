package com.apache.fastandroid.demo.designmode.chain3

import com.apache.fastandroid.demo.designmode.chain2.Handler
import com.apache.fastandroid.demo.designmode.chain2.Request
import com.apache.fastandroid.demo.designmode.chain2.Response

/**
 * Created by Jerry on 2023/2/22.
 */
class HeaderHandler:Handler {
    override fun handle(request: Request): Response? {
        val response = Response(200, "Hello World")
        return response
    }
}