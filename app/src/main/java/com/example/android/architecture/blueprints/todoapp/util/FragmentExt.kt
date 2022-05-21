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

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.math.MathUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.R
import com.apache.fastandroid.app.FastApplication
import com.apache.fastandroid.jetpack.flow.FlowViewModelFactory
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



fun Fragment.getFlowViewModelFactory(): ViewModelProvider.Factory {
    val apiHelper = FastApplication.instance.apiHelper
    val dbHelper = FastApplication.instance.databaseHelper
    return FlowViewModelFactory(apiHelper, dbHelper)
}





fun Fragment.checkPermissions(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(requireContext(), permission)
            != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

fun Fragment.hasFragment(tag: String): Boolean {
    return childFragmentManager.findFragmentByTag(tag) != null
}

fun Fragment.getDrawable(@DrawableRes drawableResId: Int): Drawable? {
    return ContextCompat.getDrawable(requireActivity(), drawableResId)
}

inline val Fragment.viewLifecycleScope: LifecycleCoroutineScope
    get() = viewLifecycleOwner.lifecycleScope


private fun Fragment.calculateGridSpanCount(): Int {
    val displayMetrics: DisplayMetrics = getResources().getDisplayMetrics()
    val displayWidth = displayMetrics.widthPixels
    val itemSize: Int = getResources().getDimensionPixelSize(R.dimen.cat_toc_item_size)
    val gridSpanCount = displayWidth / itemSize
    return MathUtils.clamp(
        gridSpanCount,
        1,
        4,
    )
}



