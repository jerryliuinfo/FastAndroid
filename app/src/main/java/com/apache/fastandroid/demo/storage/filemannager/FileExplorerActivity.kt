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
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivityFileExplorerBinding
import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.io.File

const val MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 1
const val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST = 2

class FileExplorerActivity : BaseBindingFragment<ActivityFileExplorerBinding>(ActivityFileExplorerBinding::inflate) {
    private var hasPermission = false
    private lateinit var currentDirectory: File
    private lateinit var filesList: List<File>
    private lateinit var adapter: ArrayAdapter<String>


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        mBinding.toolbar.inflateMenu(R.menu.file_manager_menu)
        setupUi()
    }

    override fun onResume() {
        super.onResume()

        hasPermission = checkStoragePermission(requireActivity())
        if (hasPermission) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                if (!Environment.isExternalStorageLegacy()) {
                    mBinding.rationaleView.visibility = View.GONE
                    mBinding.legacyStorageView.visibility = View.VISIBLE
                    return
                }
            }

            mBinding.rationaleView.visibility = View.GONE
            mBinding.filesTreeView.visibility = View.VISIBLE

            // TODO: Use getStorageDirectory instead https://developer.android.com/reference/android/os/Environment.html#getStorageDirectory()
            open(getExternalStorageDirectory())
        } else {
            mBinding.rationaleView.visibility = View.VISIBLE
            mBinding.filesTreeView.visibility = View.GONE
        }
    }

    private fun setupUi() {
        mBinding.toolbar.setOnMenuItemClickListener {
//            startActivity(Intent(requireContext(), SettingsActivity::class.java))
            FragmentContainerActivity.launch(requireActivity(),SettingsActivity::class.java)
            true
        }

        mBinding.permissionButton.setOnClickListener {
            requestStoragePermission(requireActivity())
        }

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mutableListOf<String>())
        mBinding.filesTreeView.adapter = adapter
        mBinding.filesTreeView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = filesList[position]
            open(selectedItem)
        }
    }

    private fun open(selectedItem: File) {
        if (selectedItem.isFile) {
            return openFile(requireActivity(), selectedItem)
        }

        currentDirectory = selectedItem
        filesList = getFilesList(currentDirectory)

        adapter.clear()
        adapter.addAll(filesList.map {
            if (it.path == selectedItem.parentFile.path) {
                renderParentLink(requireActivity())
            } else {
                renderItem(requireActivity(), it)
            }
        })

        adapter.notifyDataSetChanged()
    }
}
