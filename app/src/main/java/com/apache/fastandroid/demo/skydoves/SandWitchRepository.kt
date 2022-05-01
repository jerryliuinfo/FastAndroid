package com.apache.fastandroid.demo.skydoves

import com.apache.fastandroid.base.BaseRepository

/**
 * Created by Jerry on 2022/5/1.
 */
class SandWitchRepository:BaseRepository() {

    suspend fun fetchPostersByCoroutine() = disneyService.fetchDisneyPostersByCoroutine()

    fun fetchPostersByCall() = disneyService.fetchDisneyPostersByCall()

    companion object{
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