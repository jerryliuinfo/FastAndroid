package com.apache.fastandroid.network.retrofit

import com.apache.fastandroid.network.response.BaseResponse
import com.apache.fastandroid.network.util.ErrorCode
import com.apache.fastandroid.network.util.NetworkException
import retrofit2.HttpException

/**
 * Created by Jerry on 2022/4/8.
 */
open class BaseNetwork {

    protected suspend fun <T> getResult(block: suspend () -> BaseResponse<T>): Result<T> {
        for (i in 1..RETRY_COUNT) {
            try {
                val response = block()
                if (response.errorCode != ErrorCode.OK) {
                    throw NetworkException.of(response.errorCode, response.errorMsg)
                }
                if (response.data == null) {
                    throw NetworkException.of(ErrorCode.VALUE_IS_NULL, "response value is null")
                }
                return Result.success(response.data)
            } catch (throwable: Throwable) {
                if (throwable is NetworkException) {
                    return Result.failure(throwable)
                }
                if ((throwable is HttpException && throwable.code() == ErrorCode.UNAUTHORIZED)) {
                    // 这里刷新token，然后重试

                }
            }
        }
        return Result.failure(NetworkException.of(ErrorCode.VALUE_IS_NULL, "unknown"))
    }
    companion object{
        private const val RETRY_COUNT = 2

    }
}