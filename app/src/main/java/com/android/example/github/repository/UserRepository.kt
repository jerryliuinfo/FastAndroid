/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.github.repository

import androidx.lifecycle.LiveData
import com.android.example.github.AppExecutors
import com.android.example.github.api.GithubService
import com.android.example.github.db.GithubUserDao
import com.android.example.github.vo.GithubUser
import com.android.example.github.vo.Resource

/**
 * Repository that handles GithubUser objects.
 */
class UserRepository  constructor(
    private val appExecutors: AppExecutors,
    private val userDao: GithubUserDao,
    private val githubService: GithubService
) {

    fun loadUser(login: String): LiveData<Resource<GithubUser>> {
        return object : NetworkBoundResource<GithubUser, GithubUser>(appExecutors) {
            override fun saveCallResult(item: GithubUser) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: GithubUser?) = data == null

            override fun loadFromDb() = userDao.findByLogin(login)

            override fun createCall() = githubService.getUser(login)
        }.asLiveData()
    }

    companion object{
        private var sInstance:UserRepository ?= null
        fun getInstance(appExecutors: AppExecutors,userDao: GithubUserDao,githubService: GithubService):UserRepository{
            return sInstance ?: synchronized(this){
                sInstance ?:UserRepository(appExecutors,userDao,githubService).also {
                    sInstance = it
                }
            }
        }
    }
}
