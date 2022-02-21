package com.apache.fastandroid.network.model

/**
 * Created by Jerry on 2022/2/18.
 */
data class ResultData<T> (val code:String, val message:String?, val data:T)