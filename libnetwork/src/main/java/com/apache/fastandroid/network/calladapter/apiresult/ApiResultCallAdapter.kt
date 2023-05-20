package com.apache.fastandroid.network.calladapter.apiresult

import com.apache.fastandroid.network.model.result.ApiResult
import com.apache.fastandroid.network.retrofit.ApiResultCall
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ApiResultCallAdapter<T>(
    private val successType: Type,
) : CallAdapter<T, Call<ApiResult<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ApiResult<T>> = ApiResultCall(call)
}
