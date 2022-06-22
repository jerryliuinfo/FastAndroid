package com.apache.fastandroid.jetpack.flow.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource
import com.apache.fastandroid.network.model.Status
import com.tesla.framework.component.Event
import com.tesla.framework.component.LocalEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2022/6/20.
 */
@HiltViewModel
class MutableStateViewModel @Inject constructor(private val apiHelper: ApiHelper):ViewModel() {

    val mCount = MutableStateFlow(0)

    val userState = MutableStateFlow(Resource(Status.LOADING, emptyList<ApiUser>(), ""))

    fun increment(){
       mCount.apply {
           var current = ++value
           value =  current.coerceAtMost(10)
       }
    }

    fun decrement(){
        mCount.apply {
            value =  (--value).coerceAtLeast(0)
        }
    }


    private lateinit var job: Job

    fun startRefresh() {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                LocalEventBus.postEvent(Event(System.currentTimeMillis()))
                delay(100)
            }
        }
    }

    fun stopRefresh() {
        job.cancel()
    }

    init {
        getUsers()
    }

    fun getUsers(){
        viewModelScope.launch {
            userState.value = Resource.loading()
            val allUsersFromApi = mutableListOf<ApiUser>()
            apiHelper.getUsers()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    userState.value = (Resource.error(e.toString(), null))
                }.collect {
                    allUsersFromApi.addAll(it)
                    userState.value = Resource.success(allUsersFromApi)
                }

        }
    }




}