package com.apache.fastandroid.component.timerecorder.reporter

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Jerry on 2023/3/12.
 */
class AiCheckReporterImpl:ICheckEventReporter {

    private val mNextRequestId = AtomicInteger(0)

    private val mDataTranslator:AIDataTranslator by lazy { AIDataTranslator() }

    companion object{
        @Volatile
        private var sInstance: AiCheckReporterImpl?= null
        fun getInstance(): AiCheckReporterImpl {
            return sInstance ?: synchronized(AiCheckReporterImpl::class.java) {
                sInstance?: AiCheckReporterImpl().also {
                    sInstance = it
                }
            }
        }
    }

    override fun isEnable():Boolean {
        return true
    }

    override fun nextRequestId(): String {
        return mNextRequestId.getAndIncrement().toString()
    }

    override fun httpExchangeFailed(requestId: String, errorText: String?) {
    }

    override fun requestWillBeSent(request: ICheckEventReporter.IInspectorRequest) {
        mDataTranslator.saveInspectorRequest(request)

    }

    override fun responseHeadersReceived(response: ICheckEventReporter.IInspectorResponse) {
        mDataTranslator.saveInspectorResponse(response)
    }
}