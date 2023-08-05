package com.apache.fastandroid.jetpack.hit.module

import android.content.Context
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.api.ApiHelperImpl
import com.apache.fastandroid.jetpack.flow.vm.PostViewModel
import com.apache.fastandroid.network.api.ApiServiceFactory
import com.apache.fastandroid.network.api.FlowApiService
import com.google.android.gms.auth.blockstore.Blockstore
import com.google.android.gms.auth.blockstore.BlockstoreClient
import com.tesla.framework.component.eventbus.flow.EventHub
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Jerry on 2022/6/19.
 */
@InstallIn(SingletonComponent::class)
@Module
class AppModule {

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


    @Singleton
    @Provides
    fun provideEventHub() : EventHub {
        return EventHub()
    }

//    @Singleton
//    @Provides
//    fun provideBlockStoreClient(@ApplicationContext context: Context): BlockstoreClient =
//        Blockstore.getClient(context)
}