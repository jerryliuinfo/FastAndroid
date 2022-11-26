package com.apache.fastandroid.demo.repository

import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Jerry on 2022/5/16.
 */
class AppRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers2()
    fun getUsersSingle() = apiHelper.getUsersSingle()




}