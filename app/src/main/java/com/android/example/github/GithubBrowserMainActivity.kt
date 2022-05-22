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

package com.android.example.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.GithubBrowserActivityMainBinding
import com.tesla.framework.ui.activity.BaseVmActivity

class GithubBrowserMainActivity : BaseVmActivity<GithubBrowserActivityMainBinding>(GithubBrowserActivityMainBinding::inflate),
    NavController.OnDestinationChangedListener {

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        mBinding.run {
            findNavController(R.id.container).addOnDestinationChangedListener(this@GithubBrowserMainActivity)
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when(destination.id){
            R.id.searchFragment -> println("click searchFragment")
            R.id.repoFragment -> println("click repoFragment")
            R.id.userFragment -> println("click userFragment")
        }
    }

}
