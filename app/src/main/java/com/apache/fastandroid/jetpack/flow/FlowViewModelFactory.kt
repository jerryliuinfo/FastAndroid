package com.apache.fastandroid.jetpack.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.filter.FilterViewModel
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.jetpack.flow.map.MapViewModel
import com.apache.fastandroid.jetpack.flow.retry.RetryViewModel
import com.apache.fastandroid.jetpack.flow.retryexponentialbackoff.RetryExponentialBackoffModel
import com.apache.fastandroid.jetpack.flow.retrywhen.RetryWhenViewModel

/**
 * Created by Jerry on 2022/5/12.
 */
class FlowViewModelFactory(private val apiHelper: ApiHelper,private val dbHelper: DatabaseHelper):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(FilterViewModel::class.java)){
            return FilterViewModel(apiHelper,dbHelper) as T
        } else if (modelClass.isAssignableFrom(MapViewModel::class.java)){
            return MapViewModel(apiHelper,dbHelper) as T
        }else if (modelClass.isAssignableFrom(RetryViewModel::class.java)){
            return RetryViewModel(apiHelper,dbHelper) as T
        }else if (modelClass.isAssignableFrom(RetryWhenViewModel::class.java)){
            return RetryWhenViewModel(apiHelper,dbHelper) as T
        }else if (modelClass.isAssignableFrom(RetryExponentialBackoffModel::class.java)){
            return RetryExponentialBackoffModel(apiHelper,dbHelper) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}