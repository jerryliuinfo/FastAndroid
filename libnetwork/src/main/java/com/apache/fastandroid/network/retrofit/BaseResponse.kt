package com.apache.fastandroid.network.retrofit

import com.google.gson.annotations.SerializedName

/**
 * Created with Android Studio.
 * Description: 返回数据基类
 * @author: Wangjianxian
 * @date: 2020/02/24
 * Time: 16:04
 */

open class BaseResponse<T>(var data: T,var errorCode: Int = -1,var errorMsg: String = "")