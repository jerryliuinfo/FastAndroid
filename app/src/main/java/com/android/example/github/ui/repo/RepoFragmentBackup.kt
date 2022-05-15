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

package com.android.example.github.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.android.example.github.ui.common.RetryCallback
import com.android.example.github.util.autoCleared
import com.android.example.github.vo.Contributor
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.RepoFragmentBinding
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory

/**
 * The UI Controller for displaying a Github Repo's information with its contributors.
 */
class RepoFragmentBackup : Fragment(){



    val repoViewModel: RepoViewModel by viewModels {
        getViewModelFactory()
    }
    private lateinit var binding:RepoFragmentBinding




    private val params by navArgs<RepoFragmentArgs>()
    private var adapter by autoCleared<ContributorAdapter>()

    private fun initContributorList(viewModel: RepoViewModel) {
        viewModel.contributors.observe(viewLifecycleOwner, Observer { listResource ->
            // we don't need any null checks here for the adapter since LiveData guarantees that
            // it won't call us if fragment is stopped or not started.
            if (listResource?.data != null) {
                adapter.setNewInstance(listResource.data.toMutableList())
            } else {
                adapter.setNewInstance(emptyList<Contributor>().toMutableList())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<RepoFragmentBinding>(
            inflater,
            R.layout.repo_fragment,
            container,
            false
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                repoViewModel.retry()
            }
        }
        binding = dataBinding
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repoViewModel.setId(params.owner, params.name)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.repo = repoViewModel.repo

        val adapter = ContributorAdapter() {
            contributor, imageView ->
            val extras = FragmentNavigatorExtras(
                    imageView to contributor.login
            )
            findNavController().navigate(
                    RepoFragmentDirections.showUser(contributor.login, contributor.avatarUrl),
                    extras
            )
        }
        this.adapter = adapter
        binding.contributorList.adapter = adapter
        postponeEnterTransition()
        binding.contributorList.doOnPreDraw {
            startPostponedEnterTransition()
        }
        initContributorList(repoViewModel)
    }
}
