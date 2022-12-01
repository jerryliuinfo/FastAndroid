/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apache.fastandroid.demo.material

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Fragment to display a list of subsystems that show the values of this app's theme.
 */
class ThemeSummaryFragment :
    BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        val myAdapter = SubsystemAdapter()
        mBinding.recyclerView.apply {
            visibility = View.VISIBLE
            adapter = myAdapter

        }
        myAdapter.submitList(Subsystem.values().toList())
    }

}
