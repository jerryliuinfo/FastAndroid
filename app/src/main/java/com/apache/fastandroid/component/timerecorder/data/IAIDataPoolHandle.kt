package com.apache.fastandroid.component.timerecorder.data

/**
 * Created by Jerry on 2023/3/11.
 */
interface IAIDataPoolHandle {

    fun initDataPool()

    fun clearDataPool()

    /**
     *
     * @param key String: 事件 id
     * @param feedModel AICheckFeedModel:事件详情
     */
    fun addCheckFeedData(key:String, feedModel: AICheckFeedModel)

    fun removeCheckFeedData(key:String)


    fun getCheckFeedMap():HashMap<String,AICheckFeedModel>

    /**
     *
     * @param requestId String:事件id
     * @return AICheckFeedModel
     */
    fun getFeedModel(requestId:String):AICheckFeedModel?

    /**
     *
     * @param requestId String:事件 id
     * @return AICheckTraceModel
     */
    fun getTraceModel(requestId:String):AICheckTraceModel?


}