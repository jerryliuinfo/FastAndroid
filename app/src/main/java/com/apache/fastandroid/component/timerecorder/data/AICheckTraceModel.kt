package com.apache.fastandroid.component.timerecorder.data

/**
 * Created by Jerry on 2023/3/11.
 */
class AICheckTraceModel : java.io.Serializable {


    companion object {
        const val CALL_START = "callStart"
        const val CALL_END = "callEnd"

        const val PROCESS_START = "processStart"
        const val PROCESS_END = "processEnd"

        const val SERVER_START = "serverStart"
        const val SERVER_END = "serverEnd"

        const val ASR_START = "sessionStart"
        const val ASR_TEXTFLOW = "textFlow"
        const val ASR_END = "sessionEnd"






//        指标


        const val TRACE_NAME_TOTAL = "Total Time"

        const val TRACE_NAME_PROCESS = "Process"

        const val TRACE_NAME_SERVER = "SERVER"

        const val TRACE_NAME_ASR = "ASR"


    }

    var url:String ?= null

    //事件编号
    var id: String? = null

    //事件发生事件
    var time: Long? = null

    //记录每一个事件发生的时间点, 例如 processStart, processEnd
    var eventsHashMap: MutableMap<String, Long> = mutableMapOf()

    // 记录某一个事件发生的耗时，例如： processStart, processEnd 定义为 "process"事件，耗时为 (processEnd - processStart)的时间差
    var traceItemList: MutableMap<String, Long> = mutableMapOf()
    override fun toString(): String {
        return "AICheckTraceModel(url=$url, id=$id, time=$time, eventsHashMap=${eventsHashMap.map {
            "${it.key},${it.value}"
        }}, traceItemList=${traceItemList.map {
            "${it.key},${it.value}"
        }})"
    }


}