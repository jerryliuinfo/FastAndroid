package com.apache.fastandroid.jetpack.reporsity

import androidx.lifecycle.LiveData

/**
 * Created by Jerry on 2022/3/9.
 */
interface WeatherDataSource {
    fun getCurrentTime(): LiveData<Long>
    fun fetchWeather(): LiveData<String>
    val cachedData: LiveData<String>
    suspend fun fetchNewData()
}