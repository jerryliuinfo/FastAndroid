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
package com.example.android.architecture.blueprints.todoapp.util

/**
 * Extension functions for Fragment.
 */

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.app.FastApplication
import com.example.android.architecture.blueprints.todoapp.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as FastApplication).taskRepository
    return ViewModelFactory(repository, this)
}

fun Fragment.getActivityViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as FastApplication).taskRepository
    return ViewModelFactory(repository, requireActivity())
}

fun Fragment.getApplicationViewModelFactory(): ViewModelProvider.AndroidViewModelFactory {
    val repository = (requireContext().applicationContext as FastApplication).taskRepository
    return ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
}

fun ComponentActivity.getViewModelFactory(): ViewModelFactory {
    val repository = (this as FastApplication).taskRepository
    return ViewModelFactory(repository, this)
}

