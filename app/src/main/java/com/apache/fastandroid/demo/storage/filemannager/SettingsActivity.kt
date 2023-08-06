/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apache.fastandroid.demo.storage.filemannager

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityFileManagerSettingsBinding
import com.tesla.framework.kt.getString
import com.tesla.framework.ui.fragment.BaseBindingFragment

class SettingsActivity : BaseBindingFragment<ActivityFileManagerSettingsBinding>(ActivityFileManagerSettingsBinding::inflate) {
    private lateinit var binding: ActivityFileManagerSettingsBinding
    private lateinit var adapter: ArrayAdapter<String>



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, getInfoList())
        binding.infoList.adapter = adapter

        binding.openSettingsButton.setOnClickListener {
            openPermissionSettings(requireActivity())
        }
        binding.requestPermissionButton.setOnClickListener {
            requestStoragePermission(requireActivity())
        }
    }

    private fun getInfoList(): List<String> {
        return listOf(
            R.string.sdk_codename_info.getString(),
            getString(R.string.sdk_codename_info, Build.VERSION.CODENAME),
            getString(R.string.sdk_version_info, Build.VERSION.SDK_INT.toString()),
            getString(R.string.legacy_storage_info, getLegacyStorageStatus()),
            getString(R.string.permission_used_info, getStoragePermissionName()),
            getString(R.string.permission_granted_info, getPermissionStatus(requireActivity()))
        )
    }
}