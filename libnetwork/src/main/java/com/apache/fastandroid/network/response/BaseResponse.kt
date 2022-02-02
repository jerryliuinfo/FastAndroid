package com.apache.fastandroid.network.response

import java.io.Serializable


open class BaseResponse<T>(var data: T  ,var errorCode: Int = -1,var errorMsg: String = ""):Serializable