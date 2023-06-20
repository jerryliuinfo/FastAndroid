package com.apache.fastandroid.demo.designmode.chain2

/**
 * Created by Jerry on 2023/2/22.
 */
// 示例 Handler
class HeaderHandler : Handler {
    override fun handle(request: Request?): Response? {
        val response = Response(200, "Hello World")
        return Response(response.statusCode, "Response for " + request!!.url)
    }
}