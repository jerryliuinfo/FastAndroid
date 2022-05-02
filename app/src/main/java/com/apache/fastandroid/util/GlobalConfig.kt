package com.apache.fastandroid.util

import android.os.Build
import com.apache.fastandroid.BuildConfig

/**
 * Created by Jerry on 2022/5/2.
 */
object GlobalConfig {

    var spName:String = if (BuildConfig.DEBUG) "config_debug" else "config"
}