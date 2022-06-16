package com.apache.fastandroid.jetpack.hit.di

import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.api.ApiHelperImpl
import com.apache.fastandroid.jetpack.flow.local.AppDatabase
import com.apache.fastandroid.jetpack.flow.local.DatabaseBuilder
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelperImpl
import com.apache.fastandroid.jetpack.hit.engine.Engine
import com.apache.fastandroid.jetpack.hit.engine.GasEngine
import com.apache.fastandroid.network.retrofit.ApiService
import com.apache.fastandroid.network.retrofit.FlowApiService
import com.apache.fastandroid.network.retrofit.RetrofitFactory
import com.apache.fastandroid.retrofit.ApiConstant
import com.blankj.utilcode.util.Utils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by Jerry on 2022/6/16.
 */
@Module //表示这是一个提供注入实例的模块
@InstallIn(ApplicationComponent::class)
abstract class ApiModule {
    @Binds
    abstract fun bindApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper

    @Provides
    fun bindApiService(): FlowApiService{
        return RetrofitFactory.instance.create(
            FlowApiService::class.java,
            ApiConstant.FLOW_BASE_URL
        )
    }

    @Binds
    abstract fun bindDatabaseHelper(databaseHelperImpl: DatabaseHelperImpl): DatabaseHelper


    @Provides
    fun bindAAppDatabase(): AppDatabase {
        return DatabaseBuilder.getInstance(Utils.getApp())
    }
}