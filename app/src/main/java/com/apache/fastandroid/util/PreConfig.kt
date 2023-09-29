package com.apache.fastandroid.util

import com.tesla.framework.common.util.Prefs
import java.util.UUID

/**
 * Created by Jerry on 2023/9/28.
 */
object PreConfig {

    fun getAppInstallID(): String? {
        var id: String? = Prefs.appInstallId
        if (id == null) {
            id = UUID.randomUUID().toString()
            Prefs.appInstallId = id
        }
        return id
    }
}