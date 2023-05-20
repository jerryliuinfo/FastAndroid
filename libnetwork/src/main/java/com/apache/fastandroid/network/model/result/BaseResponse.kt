package com.apache.fastandroid.network.model.result

open class BaseResponse<T>(var data: T  ,var errorCode: Int = -1,var code: Int = -1,var errorMsg: String = ""){
    fun isSuccess():Boolean = errorCode == 0

    fun isFailed() = !isSuccess()
    override fun toString(): String {
        return "BaseResponse(data=$data, errorCode=$errorCode, code=$code, errorMsg='$errorMsg')"
    }


}