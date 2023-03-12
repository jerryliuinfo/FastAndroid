package com.apache.fastandroid.component.timerecorder.reporter

/**
 * Created by Jerry on 2023/3/12.
 */
interface ICheckEventReporter {

    fun isEnable(): Boolean

    fun nextRequestId(): String


    fun httpExchangeFailed(requestId: String, errorText: String?)


    fun requestWillBeSent(request: IInspectorRequest)

    fun responseHeadersReceived(response: IInspectorResponse)


    interface IInspectorRequestCommon {
        fun id(): String
    }

    interface IInspectorRequest : IInspectorRequestCommon {
        fun url(): String

        /**
         * HTTP method ("GET", "POST", "DELETE", etc).
         */
        fun method(): String

    }

    interface IInspectorHeaders {
        fun headerCount(): Int
        fun headerName(index: Int): String?
    }

    interface IInspectorResponseCommon : IInspectorHeaders {
        fun requestId(): String

        fun statusCode(): Int
    }

    interface IInspectorResponse : IInspectorResponseCommon {
        fun url(): String
    }

}