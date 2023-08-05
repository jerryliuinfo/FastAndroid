package com.apache.fastandroid.network.model.result

open class BaseResponse<T>(var data: T  ,var errorCode: Int = -1,var errorMsg: String = ""){

    override fun toString(): String {
        return "BaseResponse(data=$data, errorCode=$errorCode,  errorMsg='$errorMsg')"
    }


}