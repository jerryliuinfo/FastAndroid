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

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import java.lang.reflect.Type

internal class SyncNetworkResultCallAdapter<S : Any>(
    private val successType: Type
) : CallAdapter<S, NetworkResult<S>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): NetworkResult<S> {
        return try {
            val response = call.execute()
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                NetworkResult.success(responseBody)
            } else {
                NetworkResult.failure(HttpException(response))
            }
        } catch (e: Exception) {
            NetworkResult.failure(e)
        }
    }
}
