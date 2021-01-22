package com.kidsedu.network.api

import com.apache.fastandroid.demo.network.api.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class EduNetwork {

    private val kidsService = ServiceCreator.create(EduService::class.java)

    suspend fun fetchPrivacyData() = kidsService.getAgreementData().await()


    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object {

        private var network: EduNetwork? = null

        fun getInstance(): EduNetwork {
            if (network == null) {
                synchronized(EduNetwork::class.java) {
                    if (network == null) {
                        network = EduNetwork()
                    }
                }
            }
            return network!!
        }

    }

}