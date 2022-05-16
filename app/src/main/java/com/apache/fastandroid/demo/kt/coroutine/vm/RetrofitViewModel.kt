package com.apache.fastandroid.demo.kt.coroutine.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.apache.fastandroid.demo.repository.AppRepository
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource

/**
 * Created by Jerry on 2022/5/16.
 */
class RetrofitViewModel(private val repository:AppRepository):ViewModel() {

    fun getUsers():LiveData<Resource<List<ApiUser>>>  = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getUsers()))
        }catch (e:Exception){
            emit(Resource.error(msg = e.message ?: "error occurred", data = null))
        }
    }

}