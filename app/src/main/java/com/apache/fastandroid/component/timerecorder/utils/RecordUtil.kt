package com.apache.fastandroid.component.timerecorder.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.preference.PreferenceManager
import com.apache.fastandroid.component.timerecorder.data.AICheckTraceModel
import com.apache.fastandroid.component.timerecorder.data.AIDataPoolImpl
import com.linkaipeng.oknetworkmonitor.OkNetworkMonitor
import com.linkaipeng.oknetworkmonitor.data.NetworkTraceModel
import com.linkaipeng.oknetworkmonitor.notification.NotificationDispatcher
import com.linkaipeng.oknetworkmonitor.ui.RequestsActivity
import com.linkaipeng.oknetworkmonitor.utils.Utils
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Jerry on 2023/3/12.
 */
class RecordUtil {



    companion object{
        private val notificationId = AtomicInteger(0)

        fun getEventCostTime(
            eventsTimeMap: MutableMap<String, Long>,
            startName: String,
            endName: String
        ): Long {
            if (!eventsTimeMap.containsKey(startName) || !eventsTimeMap.containsKey(endName)) {
                return 0
            }
            return eventsTimeMap[endName]!! - eventsTimeMap[startName]!!
        }

        fun transformToTraceDetail(eventsTimeMap: MutableMap<String, Long>): LinkedHashMap<String, Long> {
            val traceDetailList = linkedMapOf<String, Long>()
            traceDetailList[AICheckTraceModel.TRACE_NAME_TOTAL] = Utils.getEventCostTime(
                eventsTimeMap,
                AICheckTraceModel.CALL_START,
                AICheckTraceModel.CALL_END
            )

            traceDetailList[AICheckTraceModel.TRACE_NAME_PROCESS] = Utils.getEventCostTime(
                eventsTimeMap,
                AICheckTraceModel.PROCESS_START,
                AICheckTraceModel.PROCESS_END
            )

            traceDetailList[AICheckTraceModel.TRACE_NAME_SERVER] = Utils.getEventCostTime(
                eventsTimeMap,
                AICheckTraceModel.SERVER_START,
                AICheckTraceModel.SERVER_END
            )

            traceDetailList[AICheckTraceModel.TRACE_NAME_ASR] = Utils.getEventCostTime(
                eventsTimeMap,
                AICheckTraceModel.ASR_START,
                AICheckTraceModel.ASR_END
            )

            return traceDetailList
        }

        fun timeoutChecker(requestId: String?) {
            if (requestId.isNullOrEmpty()) {
                return
            }
            val networkTraceModel = AIDataPoolImpl.getInstance().getTraceModel(requestId)


            val traceItemList = networkTraceModel.traceItemList
            val url = networkTraceModel.url

            check(AICheckTraceModel.TRACE_NAME_TOTAL, url, traceItemList)
            check(AICheckTraceModel.TRACE_NAME_PROCESS, url, traceItemList)
            check(AICheckTraceModel.TRACE_NAME_SERVER, url, traceItemList)
            check(AICheckTraceModel.TRACE_NAME_ASR, url, traceItemList)

        }

        private fun check(key: String, url: String?, traceItemList: MutableMap<String, Long>) {
            val context = OkNetworkMonitor.context
            if (context == null) {
                Log.d("OkNetworkMonitor", "context is null.")
                return
            }

            val costTime = traceItemList[key]
            if (isTimeout(context, key, costTime)) {
                val title = "Timeout warning. $key cost ${costTime}ms."
                val content = "url: $url"
                val intent = Intent(context, RequestsActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                NotificationDispatcher.showNotification(context, title, content,
                    pendingIntent, notificationId.getAndIncrement())
            }
        }

        private fun isTimeout(context: Context, key: String, costTime: Long?): Boolean {
            val value = getSettingTimeout(context, key)
            if (value <= 0 || costTime == null) {
                return false
            }
            return costTime > value
        }


        private fun getSettingTimeout(context: Context, key: String): Int {
            return try {
                PreferenceManager.getDefaultSharedPreferences(context).getString(key, "0")?.toInt() ?: 0
            } catch (e: Exception) {
                0
            }
        }
    }



}