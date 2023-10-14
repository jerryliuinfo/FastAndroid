/*
 * Copyright 2022 Conny Duck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apache.fastandroid.network.calladapter.networkresult

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

internal class NetworkResultCall<S : Any>(
    private val delegate: Call<S>
) : Call<NetworkResult<S>> {

    override fun enqueue(callback: Callback<NetworkResult<S>>) {
        return delegate.enqueue(
            object : Callback<S> {
                override fun onResponse(call: Call<S>, response: Response<S>) {
                    val body = response.body()

                    if (response.isSuccessful) {
                        if (body != null) {
                            callback.onResponse(
                                this@NetworkResultCall,
                                Response.success(NetworkResult.success(body))
                            )
                        } else {
                            // Response is successful but the body is null
                            callback.onResponse(
                                this@NetworkResultCall,
                                Response.success(
                                    NetworkResult.failure(HttpException(response))
                                )
                            )
                        }
                    } else {
                        callback.onResponse(
                            this@NetworkResultCall,
                            Response.success(
                                NetworkResult.failure(HttpException(response))
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<S>, throwable: Throwable) {
                    val networkResponse = NetworkResult.failure<S>(throwable)

                    callback.onResponse(this@NetworkResultCall, Response.success(networkResponse))
                }
            }
        )
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResultCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResult<S>> {
        throw UnsupportedOperationException("NetworkResultCall doesn't support synchronized execution")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
