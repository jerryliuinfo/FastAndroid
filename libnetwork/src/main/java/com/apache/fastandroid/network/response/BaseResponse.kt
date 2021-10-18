package com.apache.fastandroid.network.response


open class BaseResponse<T>(var data: T,var errorCode: Int = -1,var errorMsg: String = "")