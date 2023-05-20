package com.apache.fastandroid.di

import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.api.ApiHelperImpl
import com.apache.fastandroid.jetpack.flow.vm.PostViewModel
import com.apache.fastandroid.network.api.ApiServiceFactory
import com.apache.fastandroid.network.api.FlowApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Jerry on 2022/6/19.
 */
@InstallIn(SingletonComponent::class)
@Module
class AppModule {
//    @SingletonByObject
//    @Provides
//    fun provideRepository(): Repository {
//        return Repository()
//    }

    @Singleton
    @Provides
    fun provideFlowApiService(): FlowApiService {
        return ApiServiceFactory.flowService
    }

    @Singleton
    @Provides
    fun provideApiHelper(apiService: FlowApiService) : ApiHelper {
        return ApiHelperImpl(apiService)
    }

    @Singleton
    @Provides
    fun providePostViewModel(apiHelper: ApiHelper) : PostViewModel {
        return PostViewModel(apiHelper)
    }
}