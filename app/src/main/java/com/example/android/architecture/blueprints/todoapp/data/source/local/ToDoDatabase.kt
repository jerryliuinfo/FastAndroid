/*
 * Copyright (C) 2019 The Android Open Source Project
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

package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apache.fastandroid.demo.room.AccountDao
import com.apache.fastandroid.demo.room.AccountDatabase
import com.blankj.utilcode.util.Utils
import com.example.android.architecture.blueprints.todoapp.data.Task

/**
 * The Room Database that contains the Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao(): TasksDao



    companion object {

        private val db: ToDoDatabase by lazy {
            Room.databaseBuilder(
                Utils.getApp(),
                ToDoDatabase::class.java, "tasks"
            )
                .allowMainThreadQueries().build()
        }

        fun getInstance(): TasksDao {
            return db.taskDao()
        }
    }

}
