package com.apache.fastandroid.demo.repository

import com.apache.fastandroid.jetpack.flow.api.ApiHelper

/**
 * Created by Jerry on 2022/5/16.
 */
class AppRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers2()

}