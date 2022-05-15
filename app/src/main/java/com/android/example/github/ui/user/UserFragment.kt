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

package com.android.example.github.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.android.example.github.ui.common.RepoListAdapter
import com.android.example.github.ui.common.RetryCallback
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.UserFragmentBinding
import com.bumptech.glide.Glide
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.tesla.framework.ui.fragment.BaseVBFragment
import java.util.concurrent.TimeUnit

class UserFragment : BaseVBFragment<UserFragmentBinding>(UserFragmentBinding::inflate) {


//    var binding by autoCleared<UserFragmentBinding>()

    private val userViewModel: UserViewModel by viewModels {
        getViewModelFactory()
    }
    private val params by navArgs<UserFragmentArgs>()
    private lateinit var myAdapter:RepoListAdapter

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        userViewModel.setLogin(params.login)

        mBinding.apply {
//            args = params
            user = userViewModel.user
            Glide.with(requireContext()).load(userViewModel.user.value?.data?.avatarUrl).into(avatar)

            retryCallback  = object : RetryCallback {
                override fun retry() {
                    userViewModel.retry()
                }
            }
            /*imageRequestListener = object: RequestListener<Drawable>, RequestListener<*, *> {
                override fun onException(
                    e: Exception?,
                    model: Nothing?,
                    target: Nothing,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Nothing?,
                    model: Nothing?,
                    target: Nothing,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            }*/
        }

        myAdapter  = RepoListAdapter(
            showFullName = false
        ) { repo ->
            findNavController().navigate(UserFragmentDirections.showRepo(repo.owner.login, repo.name))
        }
        mBinding.repoList.adapter = myAdapter
        this.myAdapter = myAdapter

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        postponeEnterTransition(1, TimeUnit.SECONDS)
        initRepoList()

    }

   /* override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<UserFragmentBinding>(
            inflater,
            R.layout.user_fragment,
            container,
            false,
            dataBindingComponent
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                userViewModel.retry()
            }
        }
        binding = dataBinding
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        // When the image is loaded, set the image request listener to start the transaction
        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }
        // Make sure we don't wait longer than a second for the image request
        postponeEnterTransition(1, TimeUnit.SECONDS)
        return dataBinding.root
    }*/

   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userViewModel.setLogin(params.login)
        binding.args = params

        binding.user = userViewModel.user
        binding.lifecycleOwner = viewLifecycleOwner
        val rvAdapter = RepoListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors,
            showFullName = false
        ) { repo ->
            findNavController().navigate(UserFragmentDirections.showRepo(repo.owner.login, repo.name))
        }
        binding.repoList.adapter = rvAdapter
        this.adapter = rvAdapter
        initRepoList()
    }*/

    private fun initRepoList() {
        userViewModel.repositories.observe(viewLifecycleOwner, Observer { repos ->
            myAdapter.setNewInstance(repos?.data?.toMutableList())
        })
    }
}
