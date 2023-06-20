package com.apache.fastandroid.demo.designmode.chain2

/**
 * Created by Jerry on 2023/2/22.
 */
class Interceptor {
    private val handlers: MutableList<Handler> = ArrayList()
    fun addHandler(handler: Handler) {
        handlers.add(handler)
    }

    fun execute(request: Request): Response? {
        for (handler in handlers) {
            val response = handler.handle(request)
            if (response != null) {
                return response
            }
        }
        return null
    }
}