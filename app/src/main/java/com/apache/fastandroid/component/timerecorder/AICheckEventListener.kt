package com.apache.fastandroid.component.timerecorder

import android.os.SystemClock
import com.apache.fastandroid.component.timerecorder.data.AICheckTraceModel
import com.apache.fastandroid.component.timerecorder.data.AIDataPoolImpl
import com.apache.fastandroid.component.timerecorder.utils.RecordUtil
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Jerry on 2023/3/11.
 */
class AICheckEventListener:IEventListener {

    companion object{
        val FACTORY = object :IEventListener.Factory{
            override fun create(): IEventListener {
                return AICheckEventListener()
            }

        }
        val mNextRequestId = AtomicInteger(0)
    }

    private var mRequestId:String = mNextRequestId.get().toString()

    override fun callStart() {
        mRequestId = mNextRequestId.getAndIncrement().toString()
        saveEvent(AICheckTraceModel.CALL_START)
    }



    override fun processStart() {
        saveEvent(AICheckTraceModel.PROCESS_START)

    }

    override fun processEnd() {
        saveEvent(AICheckTraceModel.PROCESS_END)
    }

    override fun serverStart() {
        saveEvent(AICheckTraceModel.SERVER_START)

    }

    override fun serverEnd() {
        saveEvent(AICheckTraceModel.SERVER_END)

    }

    override fun asrSessionStart() {
        saveEvent(AICheckTraceModel.ASR_START)

    }

    override fun asrTextFlow() {

    }

    override fun asrSessionEnd() {
        saveEvent(AICheckTraceModel.ASR_END)
    }

    override fun callEnd() {
        saveEvent(AICheckTraceModel.CALL_END)
        generateTraceData()
        RecordUtil.timeoutChecker(mRequestId)
    }



    private fun saveEvent(eventName:String){
        val traceModel = AIDataPoolImpl.getInstance().getTraceModel(mRequestId)
        traceModel.eventsHashMap[eventName] = SystemClock.elapsedRealtime()
    }

    private fun generateTraceData(){
        val traceModel = AIDataPoolImpl.getInstance().getTraceModel(mRequestId)
        val eventsHashMap = traceModel.eventsHashMap

        val traceItemList = traceModel.traceItemList

        traceItemList[AICheckTraceModel.TRACE_NAME_TOTAL] = RecordUtil.getEventCostTime(eventsHashMap,AICheckTraceModel.CALL_START,AICheckTraceModel.CALL_END)
        traceItemList[AICheckTraceModel.TRACE_NAME_PROCESS] = RecordUtil.getEventCostTime(eventsHashMap,AICheckTraceModel.PROCESS_START,AICheckTraceModel.PROCESS_END)
        traceItemList[AICheckTraceModel.TRACE_NAME_SERVER] = RecordUtil.getEventCostTime(eventsHashMap,AICheckTraceModel.SERVER_START,AICheckTraceModel.SERVER_END)
        traceItemList[AICheckTraceModel.TRACE_NAME_ASR] = RecordUtil.getEventCostTime(eventsHashMap,AICheckTraceModel.ASR_START,AICheckTraceModel.ASR_END)
    }
}