package com.apache.fastandroid.component.timerecorder.data

/**
 * Created by Jerry on 2023/3/11.
 */
class AICheckFeedModel:java.io.Serializable {

    var mRequestId:String ?= null

    var mCreateTime:Long ?= null

    var method:String ?= null

    var mUrl: String? = null
    var mHost: String? = null


    var mCostTime: Long = 0

}