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
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResultCallAdapterFactory internal constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawReturnType = getRawType(returnType)
        // suspend functions wrap the response type in `Call`
        if (Call::class.java != rawReturnType && NetworkResult::class.java != rawReturnType) {
            return null
        }

        // check first that the return type is `ParameterizedType`
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResult<Foo>>, Call<NetworkResult<out Foo>>, NetworkResult<Foo> or NetworkResult<out Foo>"
        }

        // get the response type inside the `Call` or `NetworkResult` type
        val responseType = getParameterUpperBound(0, returnType)

        if (NetworkResult::class.java == rawReturnType) {
            return SyncNetworkResultCallAdapter<Any>(responseType)
        }

        // if the response type is not NetworkResult then we can't handle this type, so we return null
        if (getRawType(responseType) != NetworkResult::class.java) {
            return null
        }

        // the response type is Result and should be parameterized
        check(responseType is ParameterizedType) { "Response must be parameterized as Result<Foo> or Result<out Foo>" }

        val successBodyType = getParameterUpperBound(0, responseType)

        return NetworkResultCallAdapter<Any>(successBodyType)
    }

    companion object {
        @JvmStatic
        fun create(): NetworkResultCallAdapterFactory = NetworkResultCallAdapterFactory()
    }
}
