package com.apache.fastandroid.demo.network.reporsity

import com.kidsedu.network.api.EduNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

/**
 * Created by Jerry on 2021/1/20.
 */
class PrivacyReporsity(private val network: EduNetwork) {


    suspend fun getAgreementData() = withContext(Dispatchers.IO) {
        var data = network.fetchPrivacyData()
        data
    }



    companion object {
        private const val TAG = "PrivacyReporsity"

        private var instance: PrivacyReporsity? = null

        fun getInstance(network: EduNetwork): PrivacyReporsity {
            if (instance == null) {
                synchronized(PrivacyReporsity::class.java) {
                    if (instance == null) {
                        instance = PrivacyReporsity(network)
                    }
                }
            }
            return instance!!
        }

    }
}