package com.apache.fastandroid.component.timerecorder.interceptor

import com.apache.fastandroid.component.timerecorder.reporter.AiCheckReporterImpl
import com.apache.fastandroid.component.timerecorder.reporter.ICheckEventReporter
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by Jerry on 2023/3/12.
 */
class AICheckInterceptor:Interceptor {

    private val mEventReporter = AiCheckReporterImpl.getInstance()

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestId = mEventReporter.nextRequestId()

        val request = chain.request()

        if (mEventReporter.isEnable()){
            val inspectRequest = InspectRequest(requestId,request)
            mEventReporter.requestWillBeSent(inspectRequest)
        }

        var response:Response
        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            if (mEventReporter.isEnable()){
                mEventReporter.httpExchangeFailed(requestId, e.toString())
            }
            throw e
        }

        mEventReporter.responseHeadersReceived(InspectResponse(requestId, request, response))


        return response
    }


    private inner class InspectRequest(private val requestId:String,private val request: Request):
        ICheckEventReporter.IInspectorRequest {
        override fun url(): String {
            return request.url.toString()
        }

        override fun method(): String {
            return request.method
        }

        override fun id(): String {
            return requestId
        }

    }


    private inner class InspectResponse(private val requestId:String,private val request: Request,private val response: Response):
        ICheckEventReporter.IInspectorResponse {
        override fun url(): String {
            return request.url.toString()
        }

        override fun requestId(): String {
            return requestId
        }

        override fun statusCode(): Int {
            return response.code
        }

        override fun headerCount(): Int {
            return request.headers.size
        }

        override fun headerName(index: Int): String? {
            return request.headers.name(index)
        }


    }
}