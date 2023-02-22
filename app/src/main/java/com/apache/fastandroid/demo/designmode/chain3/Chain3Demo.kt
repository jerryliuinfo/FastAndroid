package com.apache.fastandroid.demo.designmode.chain3

import com.apache.fastandroid.demo.designmode.chain2.Request
import com.apache.fastandroid.demo.designmode.chain2.Response

/**
 * Created by Jerry on 2023/2/22.
 */
class Chain3Demo {

    // 示例 Handler
    val loggingHandler: (Request) -> Response? = { request ->
        println("Making request to ${request.url}")
        null
    }

    // 示例 Handler
    val headerHandler: (Request) -> Response? = { request ->
        val response = Response(200, "Hello World")
         response
    }

    fun main() {
        val interceptor = InterceptorKt()
        interceptor.addHandler(loggingHandler)
        interceptor.addHandler(headerHandler)

        val request = Request("https://www.example.com")
        val response = interceptor.execute(request)
        println(response)
    }
}