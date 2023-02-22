package com.apache.fastandroid.demo.designmode.chain3

import com.apache.fastandroid.demo.designmode.chain2.Request
import com.apache.fastandroid.demo.designmode.chain2.Response

/**
 * Created by Jerry on 2023/2/22.
 */
class InterceptorKt {
    private val handlers = mutableListOf<(Request) -> Response?>()

    fun addHandler(handler: (Request) -> Response?) {
        handlers.add(handler)
    }

    fun execute(request: Request): Response? {
        for (handler in handlers) {
            val response = handler(request)
            if (response != null) {
                return response
            }
        }
        return null
    }
}