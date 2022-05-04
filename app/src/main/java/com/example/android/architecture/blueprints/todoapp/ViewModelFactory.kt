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
package com.example.android.architecture.blueprints.todoapp

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.apache.fastandroid.jetpack.flow.api.ApiHelperImpl
import com.apache.fastandroid.jetpack.flow.completion.CompletionViewModel
import com.apache.fastandroid.jetpack.flow.errorhandling.catch.CatchViewModel
import com.apache.fastandroid.jetpack.flow.errorhandling.emitall.EmitAllViewModel
import com.apache.fastandroid.jetpack.flow.local.DatabaseBuilder
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelperImpl
import com.apache.fastandroid.jetpack.flow.parallel.ParallelNetworkCallViewModel
import com.apache.fastandroid.jetpack.flow.room.RoomDbViewModel
import com.apache.fastandroid.jetpack.flow.serias.SerialNetworkCallViewModel
import com.apache.fastandroid.jetpack.flow.single.SingleNetworkCallViewModel
import com.apache.fastandroid.network.retrofit.ApiServiceFactory
import com.blankj.utilcode.util.Utils
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskViewModel
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.statistics.StatisticsViewModel
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailViewModel
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val tasksRepository: TasksRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(StatisticsViewModel::class.java) ->
                StatisticsViewModel(tasksRepository)
            isAssignableFrom(TaskDetailViewModel::class.java) ->
                TaskDetailViewModel(tasksRepository)
            isAssignableFrom(AddEditTaskViewModel::class.java) ->
                AddEditTaskViewModel(tasksRepository)
            isAssignableFrom(TasksViewModel::class.java) ->
                TasksViewModel(tasksRepository, handle)

            isAssignableFrom(SingleNetworkCallViewModel::class.java) ->
                SingleNetworkCallViewModel(ApiHelperImpl(ApiServiceFactory.flowService))

            isAssignableFrom(SerialNetworkCallViewModel::class.java) ->
                SerialNetworkCallViewModel(ApiHelperImpl(ApiServiceFactory.flowService))

            isAssignableFrom(ParallelNetworkCallViewModel::class.java) ->
                ParallelNetworkCallViewModel(ApiHelperImpl(ApiServiceFactory.flowService))

            isAssignableFrom(RoomDbViewModel::class.java) ->
                RoomDbViewModel(ApiHelperImpl(ApiServiceFactory.flowService),DatabaseHelperImpl(DatabaseBuilder.getInstance(Utils.getApp())))

            isAssignableFrom(CatchViewModel::class.java) ->
                CatchViewModel(ApiHelperImpl(ApiServiceFactory.flowService),DatabaseHelperImpl(DatabaseBuilder.getInstance(Utils.getApp())))

            isAssignableFrom(EmitAllViewModel::class.java) ->
                EmitAllViewModel(ApiHelperImpl(ApiServiceFactory.flowService),DatabaseHelperImpl(DatabaseBuilder.getInstance(Utils.getApp())))

            isAssignableFrom(CompletionViewModel::class.java) ->
                CompletionViewModel(ApiHelperImpl(ApiServiceFactory.flowService),DatabaseHelperImpl(DatabaseBuilder.getInstance(Utils.getApp())))
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
