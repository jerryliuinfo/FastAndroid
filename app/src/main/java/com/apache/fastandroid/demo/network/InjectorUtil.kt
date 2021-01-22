package com.apache.fastandroid.demo.network

import com.apache.fastandroid.demo.network.reporsity.PrivacyReporsity
import com.kidsedu.network.api.EduNetwork

object InjectorUtil {


    private fun getWeatherRepository() = PrivacyReporsity.getInstance(EduNetwork.getInstance())

    @JvmStatic
    fun getWeatherModelFactory() = PrivacyModelFactory(getWeatherRepository())


}