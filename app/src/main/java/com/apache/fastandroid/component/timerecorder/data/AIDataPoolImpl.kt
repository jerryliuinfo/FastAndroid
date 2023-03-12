package com.apache.fastandroid.component.timerecorder.data

/**
 * Created by Jerry on 2023/3/11.
 */
class AIDataPoolImpl : IAIDataPoolHandle {

    private var mCheckFeedMap: HashMap<String, AICheckFeedModel> = hashMapOf()

    private var mTraceModelMap: HashMap<String, AICheckTraceModel> = hashMapOf()


    companion object {
        @Volatile
        private var sInstance: AIDataPoolImpl? = null
        fun getInstance(): AIDataPoolImpl {
            return sInstance ?: synchronized(AIDataPoolImpl::class.java) {
                sInstance ?: AIDataPoolImpl().also {
                    sInstance = it
                }
            }
        }
    }


    override fun initDataPool() {
        mCheckFeedMap.clear()
    }

    override fun clearDataPool() {
        mCheckFeedMap.clear()
    }

    override fun addCheckFeedData(key: String, feedModel: AICheckFeedModel) {
        mCheckFeedMap[key] = feedModel

    }

    override fun removeCheckFeedData(key: String) {
        mCheckFeedMap.remove(key)
    }

    override fun getCheckFeedMap(): HashMap<String, AICheckFeedModel> {
        return mCheckFeedMap
    }

    override fun getFeedModel(requestId: String): AICheckFeedModel {
        if (mCheckFeedMap[requestId] != null) {
            return mCheckFeedMap[requestId]!!
        }
        return AICheckFeedModel().apply {
            mRequestId = requestId
            mCreateTime = System.currentTimeMillis()
        }.also {
            mCheckFeedMap[requestId] = it
        }

    }

    override fun getTraceModel(requestId: String): AICheckTraceModel {
        return if (mTraceModelMap[requestId] != null) {
            mTraceModelMap[requestId]!!
        } else {
            AICheckTraceModel().apply {
                id = requestId
                time = System.currentTimeMillis()
            }.also {
                mTraceModelMap[requestId] = it
            }
        }
    }

    fun getTraceModelMap(): HashMap<String, AICheckTraceModel>{
        return mTraceModelMap
    }

}