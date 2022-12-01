package com.apache.fastandroid.network.interceptor

import com.apache.fastandroid.network.exception.ApiException
import okhttp3.Response
import org.json.JSONObject

/**
 * Created by Jerry on 2022/11/30.
 * code值不为 200，则抛出异常不执行后面的解析过程了
 */
class HandleErrorInterceptor : ResponseBodyInterceptor() {


    override fun intercept(response: Response, url: String, body: String): Response {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (jsonObject != null) {
            val code = jsonObject.optInt("code", -1)
            val msg = jsonObject.optString("msg", "")
            if (code != 200 && !msg.isNullOrEmpty()) {
                throw ApiException(msg=msg)
            }
        }
        return response
    }
}
