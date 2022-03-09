package com.apache.fastandroid.jetpack.viewmodel

import androidx.lifecycle.*
import com.apache.fastandroid.jetpack.reporsity.WeatherDataSource
import com.apache.fastandroid.jetpack.reporsity.impl.DefaultWeatherDataSource
import com.blankj.utilcode.util.TimeUtils
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/3/9.
 */
class WeatherInfoViewModel(private val dataSource: WeatherDataSource):ViewModel() {

    val currentTime:LiveData<Long> = dataSource.getCurrentTime()

    val currentTimeTransformed = currentTime.switchMap {
        liveData {
            Logger.d("currentTimeTransformed run in :${Thread.currentThread().name}")
            emit(timeStampToTime(it))
        }
    }

    val currentWeather:LiveData<String> = liveData {
        emit(LOADING_STRING)
        emitSource(dataSource.fetchWeather())
    }

    //Exposed cached value in the data source that can be updated later on
    val cacheData = dataSource.cachedData

    fun onRefresh(){
        Logger.d("onRefresh --->")
       // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchNewData()
        }
    }

    companion object{
        // Real apps would use a wrapper on the result type to handle this.
        const val LOADING_STRING = "Loading..."
    }

    // Simulates a long-running computation in a background thread
    private suspend fun timeStampToTime(timestamp:Long):String{
        delay(500)
        return TimeUtils.millis2String(timestamp)
    }
}

object WeatherViewModelFactory:ViewModelProvider.Factory{

    private val dataSource = DefaultWeatherDataSource(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherInfoViewModel(dataSource) as T
    }

}