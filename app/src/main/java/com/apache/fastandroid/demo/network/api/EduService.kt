package com.kidsedu.network.api

import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.demo.network.api.AgreementResponseBean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Jerry on 2021/1/20.
 */
interface EduService {
    //获取隐私数据接口
    @GET("/api/agreement/get")
    fun getAgreementData(): Call<MutableLiveData<AgreementResponseBean>>
}