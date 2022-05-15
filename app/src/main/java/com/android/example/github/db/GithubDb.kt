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

package com.android.example.github.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.android.example.github.vo.Contributor
import com.android.example.github.vo.Repo
import com.android.example.github.vo.RepoSearchResult
import com.android.example.github.vo.GithubUser
import com.apache.fastandroid.demo.sunflower.db.SunFlowDatabase
import com.apache.fastandroid.demo.sunflower.utilitis.DATABASE_GITHUB_NAME
import com.apache.fastandroid.demo.sunflower.utilitis.DATABASE_NAME
import com.apache.fastandroid.demo.sunflower.works.SeedDatabaseWorker

/**
 * Main database description.
 */
@Database(
    entities = [
        GithubUser::class,
        Repo::class,
        Contributor::class,
        RepoSearchResult::class],
    version = 3,
    exportSchema = false
)
abstract class GithubDb : RoomDatabase() {

    abstract fun userDao(): GithubUserDao

    abstract fun repoDao(): RepoDao

    companion object{

        @Volatile private var instance: GithubDb?= null

        fun getInstance(context: Context): GithubDb {
            return instance ?: synchronized(this){
                instance ?:  buildDatabase(context).also {
                    instance = it
                }
            }

        }
        private fun buildDatabase(context: Context): GithubDb {
            return Room.databaseBuilder(context, GithubDb::class.java, DATABASE_GITHUB_NAME)
                .build()
        }

    }
}
