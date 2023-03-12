package com.apache.fastandroid.component.timerecorder.reporter

import android.net.Uri
import android.text.TextUtils
import com.apache.fastandroid.component.timerecorder.data.AICheckFeedModel
import com.apache.fastandroid.component.timerecorder.data.AIDataPoolImpl
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2023/3/12.
 */
class AIDataTranslator {

    private val mStartTimeMap: HashMap<String, Long> = hashMapOf()


    fun saveInspectorRequest(request:ICheckEventReporter.IInspectorRequest){
        Logger.d("saveInspectorRequest request:$request")
        val requestId = request.id()
        mStartTimeMap[requestId] = System.currentTimeMillis()
        val networkFeedModel:AICheckFeedModel? = AIDataPoolImpl.getInstance().getFeedModel(requestId)
        val url = request.url()
        if (!TextUtils.isEmpty(url)) {
            val host = Uri.parse(url).host
            networkFeedModel?.apply {
                mHost = host
                mUrl = url
            }

        }
        networkFeedModel?.method = request.method()

    }


    fun saveInspectorResponse(response: ICheckEventReporter.IInspectorResponse){
        val requestId = response.requestId()
        val costTime: Long = if (mStartTimeMap.containsKey(requestId)) {
            System.currentTimeMillis() - mStartTimeMap[requestId]!!
        } else {
            -1
        }
        val networkFeedModel = AIDataPoolImpl.getInstance().getFeedModel(requestId)
        networkFeedModel.mCostTime = costTime


    }
}