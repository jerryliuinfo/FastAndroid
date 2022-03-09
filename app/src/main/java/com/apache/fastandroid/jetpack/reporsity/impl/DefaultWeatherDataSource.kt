package com.apache.fastandroid.jetpack.reporsity.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.apache.fastandroid.jetpack.reporsity.WeatherDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Jerry on 2022/3/9.
 */
class DefaultWeatherDataSource(private val dispatcher: CoroutineDispatcher):WeatherDataSource {

    override fun getCurrentTime(): LiveData<Long> =
        liveData {
            while (true){
                emit(System.currentTimeMillis())
                delay(1000)
            }
        }

    private val weatherConditions = listOf("Sunny", "Cloudy", "Rainy", "Stormy", "Snowy")

    override fun fetchWeather(): LiveData<String> = liveData {
        var count = 0
        while (true){
            count++
            delay(2000)
            emit(weatherConditions[count % weatherConditions.size])
        }
    }


    private val _cahceData = MutableLiveData("This is old data")
    override val cachedData: LiveData<String>
        get() = _cahceData

    override suspend fun fetchNewData() {
        withContext(Dispatchers.Main){
            _cahceData.value = "Fetching new data"
            _cahceData.value = simulateNetworkDataFetch()
        }
    }


    private var count = AtomicInteger(0)
    private suspend fun simulateNetworkDataFetch():String = withContext(Dispatchers.IO){
        delay(3000)
        "new data from request:${count.addAndGet(1)}"
    }

}