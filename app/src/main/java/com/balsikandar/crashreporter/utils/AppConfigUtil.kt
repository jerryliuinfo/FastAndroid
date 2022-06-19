package com.balsikandar.crashreporter.utils

import com.apache.fastandroid.BuildConfig

/**
 * Created by Jerry on 2022/6/18.
 */
object AppConfigUtil {

    fun isAppUpdated(): Boolean {
        val newVersionCode: Long = BuildConfig.VERSION_CODE.toLong()
        val oldVersionCode = AppPref.get(AppPref.PrefKey.PREF_LAST_VERSION_CODE_LONG) as Long
        return oldVersionCode != 0L && oldVersionCode < newVersionCode
    }

     var appVersionCode:Long = 0
        set(value) {
            AppPref.set(AppPref.PrefKey.PREF_LAST_VERSION_CODE_LONG,value)
        }

    fun isAppInstalled(): Boolean { // or data cleared
        return AppPref.get(AppPref.PrefKey.PREF_LAST_VERSION_CODE_LONG) as Long == 0L
    }
}