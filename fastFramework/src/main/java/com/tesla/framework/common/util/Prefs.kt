package com.tesla.framework.common.util

import com.blankj.utilcode.util.SPUtils
import com.tesla.framework.R
import java.util.UUID

/** Shared preferences utility for convenient POJO access.  */
object Prefs {



    fun getLastRunTime(task: String): Long {
        return SPUtils.getInstance().getLong(getLastRunTimeKey(task), 0)
    }

    fun setLastRunTime(task: String, time: Long) {
        PrefsIoUtil.setLong(getLastRunTimeKey(task), time)
    }

    private fun getLastRunTimeKey(task: String): String {
        return PrefsIoUtil.getKey(R.string.preference_key_last_run_time_format, task)
    }


    var appInstallId
        get() = PrefsIoUtil.getString(R.string.preference_key_reading_app_install_id, null)
        set(id) = PrefsIoUtil.setString(R.string.preference_key_reading_app_install_id, id)



}
