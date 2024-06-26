package com.apache.fastandroid.demo.skydoves

import com.apache.fastandroid.base.BaseRepository
import com.apache.fastandroid.network.api.ApiServiceFactory

/**
 * Created by Jerry on 2022/5/1.
 */
class SandWitchRepository:BaseRepository() {

    suspend fun fetchPostersByCoroutine() = ApiServiceFactory.disneyService.fetchDisneyPostersByCoroutine()

    fun fetchPostersByCall() = ApiServiceFactory.disneyService.fetchDisneyPostersByCall()

    companion object{
        @Volatile
        private var repository:SandWitchRepository ?= null

        fun getInstance():SandWitchRepository{
            return repository?: synchronized(SandWitchRepository::class){
                repository ?: SandWitchRepository().also {
                    repository = it
                }
            }
        }
    }
}