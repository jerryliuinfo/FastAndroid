package com.apache.fastandroid.demo.track.statistics

import EasyTrack
import android.content.Context
import com.apache.fastandroid.BuildConfig
import com.apache.fastandroid.demo.track.statistics.EventConstants.*
import com.apache.fastandroid.demo.track.statistics.SensorProvider

object StatisticsUtils{
    /**
     * Created by pengxr on 5/9/2021
     */

    private val umengProvider by lazy {
        UmengProvider()
    }

    private val sensorProvider by lazy {
        SensorProvider()
    }

    /**
     * @param context ApplicationContext
     */
    fun init(context: Context) {
        configStatistics(context)
        registerProviders(context)
    }

    private fun configStatistics(context: Context) {
        EasyTrack.debug = BuildConfig.DEBUG
        EasyTrack.referrerKeyMap = mapOf(
            CUR_PAGE to FROM_PAGE,
            CUR_TAB to FROM_TAB
        )
    }

    private fun registerProviders(context: Context) {
        EasyTrack.registerProvider(umengProvider)
        EasyTrack.registerProvider(sensorProvider)
    }
}
