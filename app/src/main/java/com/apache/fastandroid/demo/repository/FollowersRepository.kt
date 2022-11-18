package com.apache.fastandroid.demo.repository


import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Repo
import com.apache.fastandroid.network.model.Resource
import com.apache.fastandroid.network.retrofit.ApiService
import com.apache.fastandroid.network.util.request
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.CoroutineScope

/**
 * Created by idisfkj on 2019-11-22.
 * Email : idisfkj@gmail.com.
 */
class FollowersRepository(
    private val apiHelper: ApiHelper,
    scope: CoroutineScope
) : BaseRepository(scope) {

    fun getUsers2(callback: (Resource<List<ApiUser>>)-> Unit) {
        Logger.d("getUsers2 --->")

        request(scope = scope, callback = callback) {
            apiHelper.getUsers2()
        }
    }
}