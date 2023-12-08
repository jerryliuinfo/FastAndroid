package com.apache.fastandroid.util

import android.content.SharedPreferences
import com.apache.fastandroid.demo.storage.sp.SPDelegates
import com.apache.fastandroid.demo.storage.sp.SPUtils
import com.tencent.mmkv.MMKV

/**
 * Created by Jerry on 2022/5/2.
 */
object GlobalValues {

    const val WORKING_MODE_URL_SCHEME = "url_scheme"
    const val WORKING_MODE_ROOT = "root"
    const val WORKING_MODE_SHIZUKU = "shizuku"

    private fun getPreferences(): SharedPreferences {
        return SPUtils.sp
    }


    val mmkv: MMKV =
        MMKV.mmkvWithID(GlobalConfig.spName) ?: throw IllegalStateException("mmkv instance is null")


    var workMode:String
        get() = mmkv.decodeString(GlobalConstans.SpKey.WORK_MODE) ?: GlobalConstans.WORK_MODE1
        set(value) {
            mmkv.encode(GlobalConstans.SpKey.WORK_MODE,value)
        }

    var webdavHost
        get() = mmkv.decodeString(GlobalConstans.SpKey.PREF_WEBDAV_HOST, "").orEmpty()
        set(value) {
            mmkv.encode(GlobalConstans.SpKey.PREF_WEBDAV_HOST, value)
        }

    var advancedOptions: Int by SPDelegates("", 10)

}